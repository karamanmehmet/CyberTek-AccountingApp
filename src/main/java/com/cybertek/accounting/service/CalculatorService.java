package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.InvoiceDto;

public interface CalculatorService {

    double calculateTax(InvoiceDto invoiceDto) throws Exception;

    double calculateCost(InvoiceDto invoiceDto) throws Exception;

    double calculateTotalCost(InvoiceDto invoiceDto) throws Exception;

}
