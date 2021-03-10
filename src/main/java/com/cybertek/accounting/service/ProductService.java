package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.dto.ProductDto;
import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.exception.*;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

public interface ProductService {

    ProductDto create(ProductDto productDto) throws ProductFieldNullException, ProductAlreadyExistException, CompanyNotFoundException;

    ProductDto findById(Long id) throws ProductNotFoundException;

    List<ProductDto> findAll();

    List<ProductDto> findByCategory(CategoryDto categoryDto) throws CategoryNotFoundException;

    List<ProductDto> findByCategoryAndStatus(CategoryDto categoryDto, boolean enabled) throws CategoryNotFoundException;

    List<ProductDto> findByCompany(CompanyDto companyDto) throws CompanyNotFoundException;

    List<ProductDto> findByCompanyAndStatus(CompanyDto companyDto, boolean enabled) throws CompanyNotFoundException;

    ProductDto update(ProductDto productDto) throws ProductFieldNullException, ProductNotFoundException, AccessDeniedException;

    void delete(long id) throws ProductNotFoundException, CompanyNotFoundException;

    void deleteByCategory(List<ProductDto> productDto);




}
