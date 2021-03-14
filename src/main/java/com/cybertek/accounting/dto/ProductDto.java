package com.cybertek.accounting.dto;

import com.cybertek.accounting.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private long id;
    private String name;
    private String description;
    private int qty;
    private CategoryDto category;
    private Unit unit;
    private CompanyDto company;
    private Boolean enabled;

}
