package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.exception.CategoryHasProductException;
import com.cybertek.accounting.exception.CategoryNotFoundException;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.CategoryAlreadyExistException;

import java.util.List;

public interface CategoryService {

    CategoryDto create(CategoryDto categoryDto) throws CategoryAlreadyExistException, CompanyNotFoundException;

    CategoryDto findById(Long id) throws CategoryNotFoundException;

    List<CategoryDto> findAll() throws CompanyNotFoundException;

    List<CategoryDto> findAllByCompany(CompanyDto companyDto);

    List<CategoryDto> findAllByCompanyAndStatus(CompanyDto companyDto, boolean enabled);

    CategoryDto update(CategoryDto categoryDto,long id) throws CategoryNotFoundException, CompanyNotFoundException, CategoryAlreadyExistException;

    void delete(long id) throws CategoryNotFoundException, CategoryHasProductException;

    CategoryDto update(CategoryDto categoryDto) throws CategoryNotFoundException, CompanyNotFoundException;

    void delete(CategoryDto categoryDto) throws CategoryNotFoundException, CategoryHasProductException;


}
