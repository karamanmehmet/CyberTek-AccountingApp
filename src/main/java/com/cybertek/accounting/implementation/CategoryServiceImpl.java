package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.ProductDto;
import com.cybertek.accounting.entity.Category;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.Product;
import com.cybertek.accounting.entity.User;
import com.cybertek.accounting.exception.*;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.repository.CategoryRepository;
import com.cybertek.accounting.repository.CompanyRepository;
import com.cybertek.accounting.repository.UserRepository;
import com.cybertek.accounting.service.CategoryService;
import com.cybertek.accounting.service.CompanyService;
import com.cybertek.accounting.service.ProductService;
import com.cybertek.accounting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final MapperGeneric mapper;
    private final ProductService productService;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;



    @Transactional
    @Override
    public CategoryDto create(CategoryDto categoryDto) throws CategoryAlreadyExistException, CompanyNotFoundException {

        Company company = getCompanyFromSecurity();
        Optional<Category> foundedCategory = repository.findByDescriptionAndCompany(categoryDto.getDescription(),company);

        if(foundedCategory.isPresent())
            throw new CategoryAlreadyExistException("Category Already exist");

        Category convertedCategory = mapper.convert(categoryDto, new Category());
        convertedCategory.setCompany(company);
        convertedCategory.setEnabled(true);

        return mapper.convert(repository.saveAndFlush(convertedCategory),new CategoryDto());

    }

    @Override
    public CategoryDto findById(Long id) throws CategoryNotFoundException {

        Category foundedCategory = repository.findById(id)
                .orElseThrow(()->new CategoryNotFoundException("This category does not exist"));
        return mapper.convert(foundedCategory,new CategoryDto());
    }

    @Override
    public List<CategoryDto> findAll() throws CompanyNotFoundException {

        Company company = getCompanyFromSecurity();

        List<Category> list = repository.findAllByCompany(company);

        return list.stream().sorted(Comparator.comparing(obj->!obj.isEnabled(),Boolean::compareTo))
                .map(obj->
                { return mapper.convert(obj,new CategoryDto()); })
                .collect(Collectors.toList());
    }

    // No need
    @Override
    public List<CategoryDto> findAllByCompany(CompanyDto companyDto) {

        Company convertedCompany = mapper.convert(companyDto, new Company());
        List<Category> list = repository.findAllByCompany(convertedCompany);

        return list.stream().sorted(Comparator.comparing(obj->!obj.isEnabled(),Boolean::compareTo))
                .map(obj->
                { return mapper.convert(obj,new CategoryDto()); })
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> findAllByStatus(boolean enabled) {

        Company company = getCompanyFromSecurity();

        List<Category> list = repository.findAllByCompanyAndEnabled(company,enabled);

        return list.stream().sorted(Comparator.comparing(obj->!obj.isEnabled(),Boolean::compareTo))
                .map(obj->
                { return mapper.convert(obj,new CategoryDto()); })
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CategoryDto update(CategoryDto categoryDto,long id) throws CategoryNotFoundException, CompanyNotFoundException, CategoryAlreadyExistException {

        Company company = getCompanyFromSecurity();

        Optional<Category> foundedCategory = repository.findById(id);
        Optional<Category> updatedCategory = repository.findByDescriptionAndCompany(categoryDto.getDescription(), company);

        if (foundedCategory.isEmpty()) {
            throw new CategoryNotFoundException("There is no category ");
        }
        if(updatedCategory.isPresent() && !updatedCategory.get().getDescription().equals(foundedCategory.get().getDescription())){
            throw new CategoryAlreadyExistException("This category already exist");
        }

        Category convertedCategory = mapper.convert(categoryDto, new Category());
        convertedCategory.setId(foundedCategory.get().getId());
        convertedCategory.setEnabled(true);
        convertedCategory.setCompany(company);

        return mapper.convert(repository.saveAndFlush(convertedCategory),new CategoryDto());
    }

    @Transactional
    @Override
    public void delete(long id) throws CategoryNotFoundException, CompanyNotFoundException {

        Company company = getCompanyFromSecurity();

        Category foundedCategory = repository.findById(id)
                .orElseThrow(()->new CategoryNotFoundException("There is no record with this "));

        Company foundedCompany = companyRepository.findByEmail(foundedCategory.getCompany().getEmail()).orElseThrow(() -> new CompanyNotFoundException("No Company Found"));

        if (company != foundedCompany) {
            throw new AccessDeniedException("Access Denied");
        }

        CategoryDto convertedCategory = mapper.convert(foundedCategory, new CategoryDto());

        List<ProductDto> products = productService.findByCategory(convertedCategory);

        if(products.size()>0) {
            productService.deleteByCategory(products);
        }

        foundedCategory.setEnabled(false);

        repository.saveAndFlush(foundedCategory);

    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) throws CategoryNotFoundException, CompanyNotFoundException {
        return null;
    }

    @Override
    public void delete(CategoryDto categoryDto) throws CategoryNotFoundException, CategoryHasProductException {

    }

    // Should be under UserRepo
    private Company getCompanyFromSecurity() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email);
        return user.getCompany();
    }

}
