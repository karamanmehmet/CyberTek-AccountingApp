package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.dto.CompanyDto;

import java.util.List;

public interface CategoryService {

    CategoryDto create(CategoryDto categoryDto) throws Exception;

    CategoryDto findById(long id) throws Exception;

    List<CategoryDto> findAll();

    List<CategoryDto> findByCompany(CompanyDto companyDto);

    List<CategoryDto> findByCompanyAndStatus(CompanyDto companyDto, boolean enabled);

    CategoryDto update(CategoryDto categoryDto) throws Exception;

    void delete(CategoryDto categoryDto) throws Exception;

}
