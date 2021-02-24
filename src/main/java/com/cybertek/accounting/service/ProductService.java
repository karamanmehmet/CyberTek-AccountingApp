package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.dto.ProductDto;
import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.entity.Category;

import java.util.List;

public interface ProductService {

    ProductDto create(ProductDto productDto) throws Exception;

    ProductDto findById(long id) throws Exception;

    List<ProductDto> findAll();

    List<ProductDto> findByCategory(CategoryDto categoryDto);

    List<ProductDto> findByCategoryAndStatus(CategoryDto categoryDto, boolean enabled);

    List<ProductDto> findByCompany(CompanyDto companyDto);

    List<ProductDto> findByCompanyAndStatus(CompanyDto companyDto, boolean enabled);

    ProductDto update(ProductDto productDto) throws Exception;

    void delete(ProductDto productDto) throws Exception;



}
