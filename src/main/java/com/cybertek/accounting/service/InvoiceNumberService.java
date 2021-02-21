package com.cybertek.accounting.service;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.InvoiceNumber;

public interface InvoiceNumberService {

   String findInvoiceNumberByCompanyAndYear(Company company,int year);

}
