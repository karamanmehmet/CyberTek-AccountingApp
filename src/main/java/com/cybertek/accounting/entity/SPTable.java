package com.cybertek.accounting.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class SPTable extends BaseEntity {

    @Column(unique = true,nullable = false)
    private String companyName;

    private String phone;

    @Column(unique = true,nullable = false)
    private String email;

    private String type;

    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;



}
