package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.InvoiceNumberNotFoundException;

import java.util.List;

public interface InvoiceNumberService {

   String create(CompanyDto companyDto) throws CompanyNotFoundException;

   List<String> findInvoiceNumberByCompanyAndYear(CompanyDto company, int year) throws CompanyNotFoundException, InvoiceNumberNotFoundException;

   List<String> findInvoiceNumberByCompany(Company company) throws CompanyNotFoundException, InvoiceNumberNotFoundException;

}
