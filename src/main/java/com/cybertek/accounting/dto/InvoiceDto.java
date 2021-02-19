package com.cybertek.accounting.dto;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.SPTable;
import com.cybertek.accounting.enums.InvoiceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceDto {

    private boolean approved;
    private String invoiceNo;
    private InvoiceType invoiceType;
    private LocalDate invoiceDate;
    private SPTableDto sptable;
    private CompanyDto company;
    double totalPrice;

}
