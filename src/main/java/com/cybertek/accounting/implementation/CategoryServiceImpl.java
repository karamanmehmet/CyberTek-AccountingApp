package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.ProductDto;
import com.cybertek.accounting.entity.Category;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.Product;
import com.cybertek.accounting.exception.*;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.repository.CategoryRepository;
import com.cybertek.accounting.service.CategoryService;
import com.cybertek.accounting.service.CompanyService;
import com.cybertek.accounting.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final MapperGeneric mapper;
    private final ProductService productService;
    private final CompanyService companyService;

    @Transactional
    @Override
    public CategoryDto create(CategoryDto categoryDto) throws CategoryAlreadyExistException, CompanyNotFoundException {

        // TODO This part will update according to valid user
        Company convertedCompany = mapper.convert(companyService.findByEmail("karaman@crustycloud.com"), new Company());

        Optional<Category> foundedCategory = repository.findByDescriptionAndCompany(categoryDto.getDescription(),convertedCompany);

        if(foundedCategory.isPresent())
            throw new CategoryAlreadyExistException("Category Already exist");

        Category convertedCategory = mapper.convert(categoryDto, new Category());
        convertedCategory.setCompany(convertedCompany);
        convertedCategory.setEnabled(true);

        return mapper.convert(repository.saveAndFlush(convertedCategory),new CategoryDto());

    }

    @Override
    public CategoryDto findById(long id) throws CategoryNotFoundException {

        Category foundedCategory = repository.findById(id)
                .orElseThrow(()->new CategoryNotFoundException("This category does not exist"));
        return mapper.convert(foundedCategory,new CategoryDto());
    }

    @Override
    public List<CategoryDto> findAll() {

        List<Category> list = repository.findAll();

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new CategoryDto()); })
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> findAllByCompany(CompanyDto companyDto) {

        Company convertedCompany = mapper.convert(companyDto, new Company());
        List<Category> list = repository.findAllByCompany(convertedCompany);

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new CategoryDto()); })
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> findAllByCompanyAndStatus(CompanyDto companyDto, boolean enabled) {

        Company convertedCompany = mapper.convert(companyDto, new Company());

        List<Category> list = repository.findAllByCompanyAndEnabled(convertedCompany,enabled);

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new CategoryDto()); })
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CategoryDto update(CategoryDto categoryDto,long id) throws CategoryNotFoundException, CompanyNotFoundException {
        // TODO This part will update according to valid user
        Company convertedCompany = mapper.convert(companyService.findByEmail("karaman@crustycloud.com"), new Company());
        Optional<Category> foundedCategory = repository.findById(id);

        if (foundedCategory.isEmpty()) {
            throw new CategoryNotFoundException("There is no category");
        }
        Category convertedCategory = mapper.convert(categoryDto, new Category());
        convertedCategory.setId(foundedCategory.get().getId());
        convertedCategory.setEnabled(true);
        convertedCategory.setCompany(convertedCompany);

        return mapper.convert(repository.saveAndFlush(convertedCategory),new CategoryDto());
    }




    @Transactional
    @Override
    public void delete(long id) throws CategoryNotFoundException, CategoryHasProductException {

        Category foundedCategory = repository.findById(id)
                .orElseThrow(()->new CategoryNotFoundException("There is no record with this "));

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

    @Transactional
    @Override
    public void delete(CategoryDto categoryDto) throws CategoryNotFoundException, CategoryHasProductException {
     /*   Category foundedCategory = repository.findById(categoryDto.getId())
                .orElseThrow(()->new CategoryNotFoundException("There is no record with this "));

        CategoryDto convertedCategory = mapper.convert(foundedCategory, new CategoryDto());

        List<ProductDto> products = productService.findByCategory(convertedCategory);

        if(products.size()>0) {
            productService.deleteByCategory(products);
        }

        foundedCategory.setEnabled(false);

        repository.saveAndFlush(foundedCategory);*/

    }
}
