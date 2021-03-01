package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.dto.InvoiceProductDto;
import com.cybertek.accounting.dto.ProductDto;
import com.cybertek.accounting.exception.InvoiceNotFoundException;
import com.cybertek.accounting.exception.InvoiceProductNotFoundException;
import com.cybertek.accounting.exception.NotEnoughProductInStockException;
import com.cybertek.accounting.exception.ProductNotFoundException;

import java.util.List;

public interface InvoiceProductService {

    InvoiceProductDto create(InvoiceProductDto invoiceProduct) throws InvoiceProductNotFoundException, InvoiceNotFoundException, ProductNotFoundException, NotEnoughProductInStockException;

    InvoiceProductDto update(InvoiceProductDto invoiceProduct) throws InvoiceProductNotFoundException, InvoiceNotFoundException, ProductNotFoundException, NotEnoughProductInStockException;

    void delete(InvoiceProductDto invoiceProduct) throws InvoiceProductNotFoundException, InvoiceNotFoundException, ProductNotFoundException, NotEnoughProductInStockException;

    List<InvoiceProductDto> findAll();

    InvoiceProductDto findByInvoiceAndProduct(InvoiceDto invoice, ProductDto product) throws InvoiceProductNotFoundException, ProductNotFoundException;

//    InvoiceProductDto findById(long id);



}
