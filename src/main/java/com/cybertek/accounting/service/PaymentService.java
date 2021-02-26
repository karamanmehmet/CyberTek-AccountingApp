package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.InvoiceMonetaryDetailDto;
import com.cybertek.accounting.dto.InvoiceDto;

public interface PaymentService {

    InvoiceMonetaryDetailDto create(InvoiceDto invoiceDto) throws Exception;

}
