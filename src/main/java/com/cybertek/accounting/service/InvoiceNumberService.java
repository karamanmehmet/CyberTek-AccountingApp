package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.entity.InvoiceNumber;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.InvoiceAlreadyExistsException;
import com.cybertek.accounting.exception.InvoiceNumberNotFoundException;

import java.util.List;

public interface InvoiceNumberService {

   InvoiceNumber create(InvoiceDto invoiceDto, CompanyDto companyDto) throws CompanyNotFoundException, InvoiceAlreadyExistsException;

   List<InvoiceNumber> findInvoiceNumberByCompanyAndYear(CompanyDto company, int year) throws CompanyNotFoundException, InvoiceNumberNotFoundException;

   List<InvoiceNumber> findInvoiceNumberByCompany(CompanyDto company) throws CompanyNotFoundException, InvoiceNumberNotFoundException;

   InvoiceNumber findFirstByCompanyOrderByInvoiceNumberDesc(CompanyDto company) throws CompanyNotFoundException;

}
