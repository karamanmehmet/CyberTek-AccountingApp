package com.cybertek.accounting.entity;

import com.cybertek.accounting.enums.Unit;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
public class Product extends BaseEntity {

    public String name;
    public String description;
    public Integer qty;
    public BigDecimal price;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @Column(name = "category_id", nullable = false)
    public Category category;

    @Enumerated(EnumType.STRING)
    public Unit unit;

    public Integer lowLimitAlert;

    @Column(nullable = false)
    public Integer tax;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @Column(name = "company_id", nullable = false)
    public Company company;

    public Boolean isAlerted() {
        return qty <= lowLimitAlert;
    }

    public BigDecimal calculatedTax() {

        return price.multiply(BigDecimal.valueOf(tax)).divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN);
    }

}
