package com.cybertek.accounting.dto;

import com.cybertek.accounting.enums.InvoiceStatus;
import com.cybertek.accounting.enums.InvoiceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDto {

    private String invoiceNo;
    private InvoiceType invoiceType;
    private InvoiceStatus invoiceStatus;
    private LocalDate invoiceDate;
    private ClientVendorDto clientVendor;
    private CompanyDto company;
    private InvoiceMonetaryDetailDto monetaryDetailDto;

    public InvoiceDto(String invoiceNo, InvoiceType invoiceType, InvoiceStatus invoiceStatus, LocalDate invoiceDate, ClientVendorDto convertToDto, CompanyDto convertToDto1) {
    }
}
