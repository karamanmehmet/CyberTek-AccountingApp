package com.cybertek.accounting.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class InvoiceProductDto {

    private int qty;
    private double unitPrice;
    private ProductDto product;
    private InvoiceDto invoice;

    public InvoiceProductDto(int qty, double unitPrice, ProductDto product, InvoiceDto invoice) {
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.product = product;
        this.invoice = invoice;
    }
}
