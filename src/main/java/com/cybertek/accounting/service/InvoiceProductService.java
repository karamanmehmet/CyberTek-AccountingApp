package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.InvoiceProductDto;
import com.cybertek.accounting.entity.InvoiceProduct;
import java.util.List;

public interface InvoiceProductService {

    InvoiceProduct create(InvoiceProductDto invoiceProduct);

    List<InvoiceProductDto> findAll();

    InvoiceProduct update(InvoiceProductDto invoiceProduct);

    void delete(InvoiceProductDto invoiceProduct);

    InvoiceProductDto findById(long id);

    List<InvoiceProductDto> findById(Long id);

    List<InvoiceProductDto> findAllByType(String type);

}
