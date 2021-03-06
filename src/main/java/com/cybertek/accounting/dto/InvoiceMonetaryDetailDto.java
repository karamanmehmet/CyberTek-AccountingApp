package com.cybertek.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceMonetaryDetailDto {

    private double tax;
    private double cost;
    private double totalCost;
    private double balanceDue;

}
