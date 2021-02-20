package com.cybertek.accounting.service;

import com.cybertek.accounting.entity.Company;

import java.util.List;

public interface CompanyService {

    Company create(Company company);

    List<Company> findAll();

    Company update(Company company);

    void delete(Company company);

    List<Company> findByState(String state);


}
