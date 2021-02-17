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
@Table(name = "invoice_product")
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProduct extends BaseEntity{

    Integer qty;
    BigDecimal unitPrice;

    @ManyToOne()
    @JoinColumn(name = "product_id", nullable = false)
    public Product product;
    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    public Invoice invoice;




}
