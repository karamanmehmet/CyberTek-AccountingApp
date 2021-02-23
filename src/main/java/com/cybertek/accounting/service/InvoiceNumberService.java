package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.InvoiceNumber;

import java.util.List;

public interface InvoiceNumberService {

   List<String> findInvoiceNumberByCompanyAndYear(CompanyDto company, int year) throws Exception;

   List<String> findInvoiceNumberByCompany(Company company) throws Exception;

}
