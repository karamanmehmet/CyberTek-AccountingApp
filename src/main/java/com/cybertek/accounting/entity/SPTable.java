package com.cybertek.accounting.entity;

import lombok.*;

import javax.persistence.*;
@ToString
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sp_tables")
public class SPTable extends BaseEntity {

    @Column(unique = true,nullable = false)
    private String companyName;

    private String phone;

    @Column(unique = true,nullable = false)
    private String email;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="company_id",nullable = false)
    private Company company;

    private String type;




}
