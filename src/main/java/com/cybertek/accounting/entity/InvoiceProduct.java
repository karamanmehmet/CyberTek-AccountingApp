package com.cybertek.accounting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "enabled = true")
public class InvoiceProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int qty;

    @Column(nullable = false)
    private double tax;

    @Column(nullable = false)
    private double unitPrice;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = true)
    private Invoice invoice;

    private boolean enabled;

    public InvoiceProduct( int qty, double tax, double unitPrice,Product product, Invoice invoice, boolean enabled) {
        this.invoice = invoice;
        this.product = product;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.tax = tax;
        this.enabled = enabled;
    }
}
