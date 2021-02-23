package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.dto.InvoiceProductDto;
import com.cybertek.accounting.dto.ProductDto;

public interface InvoiceProductService {

    InvoiceProductDto create(InvoiceProductDto invoiceProduct) throws Exception;

    InvoiceProductDto update(InvoiceProductDto invoiceProduct) throws Exception;

    void delete(InvoiceProductDto invoiceProduct) throws Exception;

    InvoiceProductDto findByInvoiceAndProduct(InvoiceDto invoice, ProductDto product) throws Exception;

//    InvoiceProductDto findById(long id);



}
