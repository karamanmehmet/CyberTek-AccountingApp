package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.enums.InvoiceStatus;
import com.cybertek.accounting.enums.InvoiceType;
import com.cybertek.accounting.exception.*;

import java.util.List;

public interface InvoiceService {

    InvoiceDto create(InvoiceDto invoice) throws InvoiceAlreadyExistsException, CompanyNotFoundException, InvoiceNotFoundException, InvoiceProductNotFoundException;

    InvoiceDto update(InvoiceDto invoice) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException;

    boolean delete(String invoiceNo) throws InvoiceNotFoundException, CompanyNotFoundException;

    InvoiceDto approve(String invoiceNo) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException, NotEnoughProductInStockException;

    InvoiceDto archive(String invoiceNo) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException;

    Invoice findById(long id) throws InvoiceNotFoundException;

    InvoiceDto findByIdDto(long id) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException;

    InvoiceDto findByInvoiceNo(String invoiceNo) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException;

    List<InvoiceDto> findFirst3ByCompanyOrderByInvoiceDateAsc() throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException;

    List<InvoiceDto> findFirst3ByCompanyOrderByInvoiceDateDesc(CompanyDto company) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException;

    List<InvoiceDto> findAllByCompanyAndInvoiceType(CompanyDto company, InvoiceType invoiceType) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException;

    List<InvoiceDto> findAllByInvoiceType(InvoiceType invoiceType) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException;

    List<InvoiceDto> findAllByCompanyAndInvoiceStatus(CompanyDto company, InvoiceStatus invoiceStatus) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException;

    List<InvoiceDto> findAllByCompanyAndInvoiceTypeAndInvoiceStatus(CompanyDto company,InvoiceType invoiceType, InvoiceStatus invoiceStatus) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException;

}
