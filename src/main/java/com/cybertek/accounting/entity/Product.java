package com.cybertek.accounting.entity;

import com.cybertek.accounting.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    @NotNull
    private String name;

    private String description;
    private int qty;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull
    private Category category;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    private int lowLimit;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @NotNull
    private Company company;

    private Boolean enabled;

}
