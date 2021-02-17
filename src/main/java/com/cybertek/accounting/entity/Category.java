package com.cybertek.accounting.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Column(name = "description", updatable = true, nullable = false)
    public String description;

    @ManyToOne
    @Column(name = "company_id")
    public Company company;

}
