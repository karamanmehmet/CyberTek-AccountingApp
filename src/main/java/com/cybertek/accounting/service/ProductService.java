package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.dto.ProductDto;
import com.cybertek.accounting.dto.CompanyDto;

import java.util.List;

public interface ProductService {

    ProductDto create(ProductDto productDto);

    ProductDto findById(long id);

    List<ProductDto> findAll();

    List<ProductDto> findByCategory(CategoryDto categoryDto);

    List<ProductDto> findByCategoryAndStatus(CategoryDto categoryDto, boolean enabled);

    List<ProductDto> findByCompany(CompanyDto companyDto);

    List<ProductDto> findByCompanyAndStatus(CompanyDto companyDto, boolean enabled);

    ProductDto update(ProductDto productDto);

    void delete(ProductDto productDto);

}
