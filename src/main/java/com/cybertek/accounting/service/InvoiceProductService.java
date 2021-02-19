package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.InvoiceProductDto;
import com.cybertek.accounting.dto.RoleDto;
import com.cybertek.accounting.dto.SPTableDto;
import com.cybertek.accounting.entity.InvoiceProduct;
import com.cybertek.accounting.entity.Role;

import java.util.List;

public interface InvoiceProductService {
    InvoiceProduct create(InvoiceProductDto invoiceProduct);

    List<InvoiceProductDto> readAll();

    InvoiceProduct update(InvoiceProductDto invoiceProduct);

    void delete(InvoiceProductDto invoiceProduct);

    InvoiceProductDto readById(long id);

    List<InvoiceProductDto> readById(Long id);

    List<InvoiceProductDto> readAllByType(String type);
}
