package com.cybertek.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class InvoiceProductDto {

    private int qty;
    private double unitPrice;
    private ProductDto product;
    private InvoiceDto invoice;

}
