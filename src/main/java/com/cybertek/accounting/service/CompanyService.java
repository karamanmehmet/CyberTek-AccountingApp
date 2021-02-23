package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.entity.Company;

import java.util.List;

public interface CompanyService {

    CompanyDto create(CompanyDto companyDto) throws Exception;

    List<CompanyDto> findAll();

    CompanyDto update(CompanyDto companyDto) throws Exception;

    void delete(CompanyDto companyDto) throws Exception;

    List<CompanyDto> findByState(String state);


}
