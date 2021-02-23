package com.cybertek.accounting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int qty;
    private double unitPrice;

    @ManyToOne()
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = true)
    private Invoice invoice;

    private boolean isDeleted;

    public InvoiceProduct( int qty, double unitPrice,Product product, Invoice invoice) {
        this.invoice = invoice;
        this.product = product;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }
}
