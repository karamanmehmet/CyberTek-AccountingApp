package com.cybertek.accounting.entity;

import com.cybertek.accounting.enums.InvoiceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "invoice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice extends BaseEntity{


    private String invoiceNo;

    private boolean approved;

    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;

    private LocalDate invoiceDate;

    @ManyToOne
    @JoinColumn(name = "sptable_id")
    private SPTable sptable;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;


}



