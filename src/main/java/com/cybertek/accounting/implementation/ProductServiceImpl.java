package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.ProductDto;
import com.cybertek.accounting.entity.Category;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.Product;
import com.cybertek.accounting.exception.ExistentProductException;
import com.cybertek.accounting.exception.ProductNotFoundException;
import com.cybertek.accounting.exception.ProductFieldNullException;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.repository.ProductRepository;
import com.cybertek.accounting.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final MapperGeneric mapper;

    /*TODO  Should we give ID number when we creating product.
           Otherwise there is no unique value(name should be unique ?) to check product exist or Not */

    @Override
    public ProductDto create(ProductDto productDto) throws ProductFieldNullException, ExistentProductException {
        // use desc company

        if(productDto.getName()==null ||  productDto.getCategory()==null || productDto.getCompany()==null) {
            throw new ProductFieldNullException("Check your products field.They can not be null");
        }
        Optional<Product> foundedProduct = repository.findById(productDto.getId());

        if (foundedProduct.isPresent())
            throw new ExistentProductException("This product already exist");

        return mapper.convert(repository.saveAndFlush(mapper.convert(productDto,new Product())),new ProductDto());

    }

    @Override
    public ProductDto findById(long id) throws ProductNotFoundException {

        Product foundedProduct = repository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("This product does not exist"));
        return mapper.convert(foundedProduct,new ProductDto());
    }
    @Override
    public List<ProductDto> findAll() {
        List<Product> list = repository.findAll();
        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new ProductDto()); })
                .collect(Collectors.toList());
    }
    @Override
    public List<ProductDto> findByCategory(CategoryDto categoryDto) {

        Category convertedCategory = mapper.convert(categoryDto, new Category());
        List<Product> list = repository.findAllByCategory(convertedCategory);
        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new ProductDto()); })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findByCategoryAndStatus(CategoryDto categoryDto, boolean enabled) {
        Category convertedCategory = mapper.convert(categoryDto, new Category());

        List<Product> list = repository.findAllByCategoryAndEnabled(convertedCategory,enabled);

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new ProductDto()); })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findByCompany(CompanyDto companyDto) {

        Company convertedCompany = mapper.convert(companyDto, new Company());
        List<Product> list = repository.findAllByCompany(convertedCompany);

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new ProductDto()); })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findByCompanyAndStatus(CompanyDto companyDto, boolean enabled) {

        Company convertedCompany = mapper.convert(companyDto, new Company());

        List<Product> list = repository.findAllByCompanyAndEnabled(convertedCompany,enabled);

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new ProductDto()); })
                .collect(Collectors.toList());
    }


    @Override
    public ProductDto update(ProductDto productDto) throws ProductFieldNullException, ProductNotFoundException {

        if(productDto.getName()==null ||  productDto.getCategory()==null || productDto.getCompany()==null) {
            throw new ProductFieldNullException("Something went wrong please try again");
        }

        Product foundedProduct = repository.findById(productDto.getId())
                .orElseThrow(() -> new ProductNotFoundException("There is no product with this ID"));

        Product convertedProduct = mapper.convert(productDto, new Product());

        convertedProduct.setId(foundedProduct.getId());

        return mapper.convert(repository.saveAndFlush(convertedProduct),new ProductDto());

    }

    @Override
    public void delete(ProductDto productDto) throws ProductNotFoundException {

        Product foundedProduct = repository.findById(productDto.getId())
                .orElseThrow(()->new ProductNotFoundException("There is no record with this "));


        foundedProduct.setName(foundedProduct.getId()+"-"+foundedProduct.getName());

        foundedProduct.setEnabled(false);

        repository.saveAndFlush(foundedProduct);


    }

}
