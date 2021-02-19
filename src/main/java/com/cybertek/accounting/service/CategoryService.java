package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.dto.CompanyDto;

import java.util.List;

public interface CategoryService {

    CategoryDto create(CategoryDto categoryDto);

    CategoryDto findById(long id);

    List<CategoryDto> findAll();

    List<CategoryDto> findByCompany(CompanyDto companyDto);

    List<CategoryDto> findByCompanyAndStatus(CompanyDto companyDto, boolean enabled);

    CategoryDto update(CategoryDto categoryDto);

    void delete(CategoryDto categoryDto);

}
