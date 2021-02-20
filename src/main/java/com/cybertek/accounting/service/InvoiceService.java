package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.dto.RoleDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.entity.Role;

import java.util.List;

public interface InvoiceService {
    Invoice create(InvoiceDto invoice);

    List<InvoiceDto> findAll();

    Invoice update(InvoiceDto invoice);

    void delete(InvoiceDto invoice);

    Invoice findById(long id);

    List<InvoiceDto> findByInvoiceNo(String invoiceNo);

    String calculateInvoiceNo(Company company);

}
