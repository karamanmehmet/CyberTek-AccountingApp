package com.cybertek.accounting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class InvoiceNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private int year;

    private int invoiceNumber;

    public InvoiceNumber(Company company, int year, int invoiceNumber) {
        this.company = company;
        this.year = year;
        this.invoiceNumber = invoiceNumber;
    }
}
