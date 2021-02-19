package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.dto.ProductDto;
import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.entity.Product;

import java.util.List;

public interface ProductService {

    Product create(ProductDto productDto);

    ProductDto readById(long id);

    List<ProductDto> readAll();

    List<ProductDto> readByCategory(CategoryDto categoryDto);

    List<ProductDto> readByCategoryAndStatus(CategoryDto categoryDto, boolean enabled);

    List<ProductDto> readByCompany(CompanyDto companyDto);

    List<ProductDto> readByCompanyAndStatus(CompanyDto companyDto, boolean enabled);

    Product update(ProductDto productDto);

    void delete(ProductDto productDto);

}
