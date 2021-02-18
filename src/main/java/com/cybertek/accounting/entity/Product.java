package com.cybertek.accounting.entity;

import com.cybertek.accounting.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String description;
    private int qty;
    private double price;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    private int lowLimitAlert;

    @Column(nullable = false)
    private double tax;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

}
