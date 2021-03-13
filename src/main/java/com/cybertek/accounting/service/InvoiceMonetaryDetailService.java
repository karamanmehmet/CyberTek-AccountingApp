package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.InvoiceMonetaryDetailDto;
import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.InvoiceNotFoundException;
import com.cybertek.accounting.exception.InvoiceProductNotFoundException;

public interface InvoiceMonetaryDetailService {

    InvoiceMonetaryDetailDto create(InvoiceDto invoiceDto) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException;

}
