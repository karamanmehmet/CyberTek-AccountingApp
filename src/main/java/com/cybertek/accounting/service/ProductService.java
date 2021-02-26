package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.dto.ProductDto;
import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.ProductAlreadyExistException;
import com.cybertek.accounting.exception.ProductNotFoundException;
import com.cybertek.accounting.exception.ProductFieldNullException;

import java.util.List;

public interface ProductService {

    ProductDto create(ProductDto productDto) throws ProductFieldNullException, ProductAlreadyExistException, CompanyNotFoundException;

    ProductDto findById(long id) throws ProductNotFoundException;

    List<ProductDto> findAll();

    List<ProductDto> findByCategory(CategoryDto categoryDto);

    List<ProductDto> findByCategoryAndStatus(CategoryDto categoryDto, boolean enabled);

    List<ProductDto> findByCompany(CompanyDto companyDto);

    List<ProductDto> findByCompanyAndStatus(CompanyDto companyDto, boolean enabled);

    ProductDto update(ProductDto productDto) throws ProductFieldNullException, ProductNotFoundException;

    void delete(ProductDto productDto) throws ProductNotFoundException;



}
