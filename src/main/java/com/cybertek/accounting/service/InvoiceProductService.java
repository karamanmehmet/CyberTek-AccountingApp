package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.dto.InvoiceProductDto;
import com.cybertek.accounting.dto.ProductDto;
import com.cybertek.accounting.enums.InvoiceStatus;
import com.cybertek.accounting.enums.InvoiceType;
import com.cybertek.accounting.exception.*;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public interface InvoiceProductService {

    InvoiceProductDto create(InvoiceProductDto invoiceProduct) throws InvoiceProductNotFoundException, InvoiceNotFoundException, ProductNotFoundException, NotEnoughProductInStockException, CompanyNotFoundException, InvoiceAlreadyApprovedException;

    InvoiceProductDto update(InvoiceProductDto invoiceProduct) throws InvoiceProductNotFoundException, InvoiceNotFoundException, ProductNotFoundException, NotEnoughProductInStockException, CompanyNotFoundException, InvoiceAlreadyApprovedException;

    void delete(InvoiceProductDto invoiceProduct) throws InvoiceProductNotFoundException, InvoiceNotFoundException, ProductNotFoundException, NotEnoughProductInStockException, CompanyNotFoundException, InvoiceAlreadyApprovedException;

    InvoiceProductDto findById(long id) throws InvoiceProductNotFoundException;

    List<InvoiceProductDto> findAll();

    List<InvoiceProductDto> findByInvoice(InvoiceDto invoiceDto) throws CompanyNotFoundException, InvoiceNotFoundException;

    InvoiceProductDto findByInvoiceAndProduct(InvoiceDto invoice, ProductDto product) throws InvoiceProductNotFoundException, ProductNotFoundException, CompanyNotFoundException;

//    InvoiceProductDto findById(long id);

    List<InvoiceProductDto> findByInvoiceStatusAndInvoiceTypeAndCompany(CompanyDto company, InvoiceType invoiceType, InvoiceStatus invoiceStatus);

    void checkStocks(InvoiceDto invoiceDto) throws NotEnoughProductInStockException;

    Map<ProductDto, Queue<InvoiceProductDto>> findBySalesAndInvoiceStatus(InvoiceStatus invoiceStatus) throws CompanyNotFoundException;

    Map<ProductDto, Queue<InvoiceProductDto>> findByPurchaseAndInvoiceStatus(InvoiceStatus invoiceStatus) throws CompanyNotFoundException;

}
