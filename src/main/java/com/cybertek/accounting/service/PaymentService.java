package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.Payment;
import com.cybertek.accounting.dto.InvoiceDto;

public interface PaymentService {

    Payment create(InvoiceDto invoiceDto) throws Exception;

}
