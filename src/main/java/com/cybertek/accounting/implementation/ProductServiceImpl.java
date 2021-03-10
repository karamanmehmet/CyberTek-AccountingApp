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
import com.cybertek.accounting.repository.ProductRepository;
import com.cybertek.accounting.repository.UserRepository;
import com.cybertek.accounting.service.CompanyService;
import com.cybertek.accounting.service.ProductService;
import com.cybertek.accounting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final MapperGeneric mapper;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public ProductDto create(ProductDto productDto) throws ProductFieldNullException, ProductAlreadyExistException, CompanyNotFoundException {
        // TODO This part will update according to valid user

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByEmail(username);
        Company company = user.getCompany();

        productDto.setCompany(mapper.convert(company, new CompanyDto()));

        if(productDto.getName()==null ||  productDto.getCategory()==null || productDto.getCompany()== null) {
            throw new ProductFieldNullException("Check your products field.Company-Category-Name  can not be null");
        }

        Optional<Product> foundedProduct = repository.findByNameAndCompany(productDto.getName(),company);

        if (foundedProduct.isPresent())
            throw new ProductAlreadyExistException("This product already exist");

        Product convertedProduct = mapper.convert(productDto, new Product());
        //convertedCategory.setEnabled(true);

        return mapper.convert(repository.saveAndFlush(convertedProduct),new ProductDto());

    }


    @Override
    public ProductDto findById(Long id) throws ProductNotFoundException {

        Product foundedProduct = repository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("This product does not exist"));
        return mapper.convert(foundedProduct,new ProductDto());
    }

    @Override
    public List<ProductDto> findAll() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByEmail(username);
        Company company = user.getCompany();

        List<Product> list = repository.findAllByCompany(company);

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new ProductDto()); })
                .collect(Collectors.toList());
    }
    @Override
    public List<ProductDto> findByCategory(CategoryDto categoryDto) throws CategoryNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByEmail(username);
        Company company = user.getCompany();

        Category foundedCategory = categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> new CategoryNotFoundException("No Category Found"));

        List<Product> list = repository.findAllByCategoryAndCompany(foundedCategory, company);

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new ProductDto()); })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findByCategoryAndStatus(CategoryDto categoryDto, boolean enabled) throws CategoryNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByEmail(username);
        Company company = user.getCompany();

        Category foundedCategory = categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> new CategoryNotFoundException("No Category Found"));

        List<Product> list = repository.findAllByCompanyAndCategoryAndEnabled(company, foundedCategory, enabled);

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new ProductDto()); })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findByCompany(CompanyDto companyDto) throws CompanyNotFoundException {

        Company foundedCategory = companyRepository.findByEmail(companyDto.getEmail()).orElseThrow(() -> new CompanyNotFoundException("No Company Found"));

        List<Product> list = repository.findAllByCompany(foundedCategory);

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new ProductDto()); })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findByCompanyAndStatus(CompanyDto companyDto, boolean enabled) throws CompanyNotFoundException {

        Company foundedCompany = companyRepository.findByEmail(companyDto.getEmail()).orElseThrow(() -> new CompanyNotFoundException("No Company Found"));

        List<Product> list = repository.findAllByCompanyAndEnabled(foundedCompany,enabled);

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new ProductDto()); })
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ProductDto update(ProductDto productDto) throws ProductFieldNullException, ProductNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByEmail(username);
        Company company = user.getCompany();

        productDto.setCompany(mapper.convert(company, new CompanyDto()));

        if(productDto.getName()==null ||  productDto.getCategory()==null || productDto.getCompany()==null) {
            throw new ProductFieldNullException("Something went wrong please try again");
        }

        Product foundedProduct = repository.findById(productDto.getId())
                .orElseThrow(() -> new ProductNotFoundException("There is no product with this ID"));

        Product convertedProduct = mapper.convert(productDto, new Product());

        convertedProduct.setId(foundedProduct.getId());

        return mapper.convert(repository.saveAndFlush(convertedProduct),new ProductDto());

    }
    @Transactional
    @Override
    public void delete(long id) throws ProductNotFoundException, CompanyNotFoundException, AccessDeniedException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByEmail(username);
        Company company = user.getCompany();

        Product foundedProduct = repository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("There is no record with this product"));

        Company foundedCompany = companyRepository.findByEmail(foundedProduct.getCompany().getEmail()).orElseThrow(() -> new CompanyNotFoundException("No Company Found"));

        if (company != foundedCompany) {
            throw new AccessDeniedException("Access Denied");
        }

        foundedProduct.setName(foundedProduct.getId()+"-"+foundedProduct.getName());

        foundedProduct.setEnabled(false);

        repository.saveAndFlush(foundedProduct);


    }
    @Override
    public void deleteByCategory(List<ProductDto> disabledProducts)  {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByEmail(username);
        Company company = user.getCompany();

        disabledProducts.stream().forEach(productDto -> {
            try {
                if (companyRepository.findByEmail(productDto.getCompany().getEmail()).orElseThrow(() -> new CompanyNotFoundException("No Company Found")) != company) {
                    throw new AccessDeniedException("Access Denied");
                }
            } catch (CompanyNotFoundException e) {
                e.printStackTrace();
            }
        });

        List<ProductDto> disabledProductList = disabledProducts.stream()
                .peek(p -> {
                            Product product=mapper.convert(p,new Product());
                            product.setEnabled(false);
                            repository.saveAndFlush(product);
                })
                .collect(Collectors.toList());


    }



}
