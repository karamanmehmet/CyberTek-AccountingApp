package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.dto.RoleDto;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.entity.Role;

import java.util.List;

public interface InvoiceService {
    Invoice create(InvoiceDto invoice);

    List<InvoiceDto> readAll();

    Invoice update(InvoiceDto invoice);

    void delete(InvoiceDto invoice);

    Invoice readById(long id);

    List<InvoiceDto> readByInvoiceNo(String invoiceNo);

}
