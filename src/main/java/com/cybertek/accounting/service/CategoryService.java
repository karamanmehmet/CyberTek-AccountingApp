package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.entity.Category;

import java.util.List;

public interface CategoryService {

    Category create(CategoryDto categoryDto);

    CategoryDto readById(long id);

    List<CategoryDto> readAll();

    List<CategoryDto> readByCompany(CompanyDto companyDto);

    List<CategoryDto> readByCompanyAndStatus(CompanyDto companyDto, boolean enabled);

    Category update(CategoryDto categoryDto);

    void delete(CategoryDto categoryDto);

}
