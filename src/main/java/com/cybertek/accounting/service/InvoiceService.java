package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.dto.RoleDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.entity.Role;
import com.cybertek.accounting.enums.InvoiceStatus;
import com.cybertek.accounting.enums.InvoiceType;

import java.util.List;

public interface InvoiceService {

    InvoiceDto create(InvoiceDto invoice) throws Exception;

    InvoiceDto update(InvoiceDto invoice) throws Exception;

    boolean delete(InvoiceDto invoice) throws Exception;

    Invoice findById(long id) throws Exception;

    InvoiceDto findByIdDto(long id) throws Exception;


    InvoiceDto findByInvoiceNo(String invoiceNo);

    List<Invoice> findFirst3ByCompanyOrderByInvoiceDateAsc(Company company);

    List<Invoice> findFirst3ByCompanyOrderByInvoiceDateDesc(Company company);

    List<Invoice> findAllByCompanyAndInvoiceType(Company company, InvoiceType invoiceType);

    List<Invoice> findAllByCompanyAndInvoiceStatus(Company company, InvoiceStatus invoiceStatus);

    List<Invoice> findAllByCompanyAndInvoiceTypeAndInvoiceStatus(Company company,InvoiceType invoiceType, InvoiceStatus invoiceStatus);

}
