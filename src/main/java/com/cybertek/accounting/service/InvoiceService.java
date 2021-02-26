package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.enums.InvoiceStatus;
import com.cybertek.accounting.enums.InvoiceType;
import com.cybertek.accounting.exception.InvoiceNotFoundException;

import java.util.List;

public interface InvoiceService {

    InvoiceDto create(InvoiceDto invoice) throws Exception;

    InvoiceDto update(InvoiceDto invoice) throws InvoiceNotFoundException;

    boolean delete(InvoiceDto invoice) throws InvoiceNotFoundException;

    Invoice findById(long id) throws InvoiceNotFoundException;

    InvoiceDto findByIdDto(long id) throws InvoiceNotFoundException;


    InvoiceDto findByInvoiceNo(String invoiceNo);

    List<InvoiceDto> findFirst3ByCompanyOrderByInvoiceDateAsc(CompanyDto company);

    List<InvoiceDto> findFirst3ByCompanyOrderByInvoiceDateDesc(CompanyDto company);

    List<InvoiceDto> findAllByCompanyAndInvoiceType(CompanyDto company, InvoiceType invoiceType);

    List<InvoiceDto> findAllByCompanyAndInvoiceStatus(CompanyDto company, InvoiceStatus invoiceStatus);

    List<InvoiceDto> findAllByCompanyAndInvoiceTypeAndInvoiceStatus(CompanyDto company,InvoiceType invoiceType, InvoiceStatus invoiceStatus);

}
