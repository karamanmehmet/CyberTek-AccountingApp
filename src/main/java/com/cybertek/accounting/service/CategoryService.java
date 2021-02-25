package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.exception.CategoryHasProductException;
import com.cybertek.accounting.exception.CategoryNotFoundException;
import com.cybertek.accounting.exception.ExistentCategoryException;

import java.util.List;

public interface CategoryService {

    CategoryDto create(CategoryDto categoryDto) throws ExistentCategoryException;

    CategoryDto findById(long id) throws CategoryNotFoundException;

    List<CategoryDto> findAll();

    List<CategoryDto> findAllByCompany(CompanyDto companyDto);

    List<CategoryDto> findAllByCompanyAndStatus(CompanyDto companyDto, boolean enabled);

    CategoryDto update(CategoryDto categoryDto) throws CategoryNotFoundException;

    void delete(CategoryDto categoryDto) throws CategoryNotFoundException, CategoryHasProductException;



}
