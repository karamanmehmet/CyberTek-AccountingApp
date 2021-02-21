package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.InvoiceProductDto;
import com.cybertek.accounting.entity.InvoiceProduct;
import java.util.List;

public interface InvoiceProductService {

    InvoiceProductDto create(InvoiceProductDto invoiceProduct);

    InvoiceProductDto update(InvoiceProductDto invoiceProduct);

    boolean delete(InvoiceProductDto invoiceProduct);

    InvoiceProductDto findById(long id);



}
