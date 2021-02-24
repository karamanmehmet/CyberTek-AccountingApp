package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.entity.Company;

import java.util.List;

public interface InvoiceNumberService {

   String create(CompanyDto companyDto) throws Exception;

   List<String> findInvoiceNumberByCompanyAndYear(CompanyDto company, int year) throws Exception;

   List<String> findInvoiceNumberByCompany(Company company) throws Exception;

}
