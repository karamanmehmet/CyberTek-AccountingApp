package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.exception.CompanyAlreadyExistsException;
import com.cybertek.accounting.exception.CompanyNotFoundException;

import java.util.List;

public interface CompanyService {

    CompanyDto findById(Long id) throws CompanyNotFoundException;

    CompanyDto findByEmail(String email) throws CompanyNotFoundException;

    CompanyDto create(CompanyDto companyDto) throws CompanyAlreadyExistsException;

    List<CompanyDto> findAll();

    CompanyDto update(CompanyDto companyDto) throws CompanyNotFoundException;

    boolean delete(CompanyDto companyDto) throws CompanyNotFoundException;

    List<CompanyDto> findByState(String state);


}
