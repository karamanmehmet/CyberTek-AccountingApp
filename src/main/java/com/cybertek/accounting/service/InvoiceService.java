package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.enums.InvoiceStatus;
import com.cybertek.accounting.enums.InvoiceType;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.InvoiceAlreadyExistsException;
import com.cybertek.accounting.exception.InvoiceNotFoundException;
import com.cybertek.accounting.exception.InvoiceProductNotFoundException;

import java.util.List;

public interface InvoiceService {

    InvoiceDto create(InvoiceDto invoice) throws InvoiceAlreadyExistsException, CompanyNotFoundException, InvoiceNotFoundException, InvoiceProductNotFoundException;

    InvoiceDto update(InvoiceDto invoice) throws InvoiceNotFoundException, InvoiceProductNotFoundException;

    boolean delete(InvoiceDto invoice) throws InvoiceNotFoundException;

    Invoice findById(long id) throws InvoiceNotFoundException;

    InvoiceDto findByIdDto(long id) throws InvoiceNotFoundException, InvoiceProductNotFoundException;


    InvoiceDto findByInvoiceNo(String invoiceNo) throws InvoiceNotFoundException, InvoiceProductNotFoundException;

    List<InvoiceDto> findFirst3ByCompanyOrderByInvoiceDateAsc(CompanyDto company) throws InvoiceNotFoundException, InvoiceProductNotFoundException;

    List<InvoiceDto> findFirst3ByCompanyOrderByInvoiceDateDesc(CompanyDto company) throws InvoiceNotFoundException, InvoiceProductNotFoundException;

    List<InvoiceDto> findAllByCompanyAndInvoiceType(CompanyDto company, InvoiceType invoiceType) throws InvoiceNotFoundException, InvoiceProductNotFoundException;

    List<InvoiceDto> findAllByCompanyAndInvoiceStatus(CompanyDto company, InvoiceStatus invoiceStatus) throws InvoiceNotFoundException, InvoiceProductNotFoundException;

    List<InvoiceDto> findAllByCompanyAndInvoiceTypeAndInvoiceStatus(CompanyDto company,InvoiceType invoiceType, InvoiceStatus invoiceStatus) throws InvoiceNotFoundException, InvoiceProductNotFoundException;

}
