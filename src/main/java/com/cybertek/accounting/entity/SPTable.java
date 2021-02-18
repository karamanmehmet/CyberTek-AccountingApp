package com.cybertek.accounting.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@ToString
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sp_tables")
public class SPTable extends BaseEntity {

    @Column(unique = true)
    @NotNull
    private String companyName;

    private String phone;

    @Column(unique = true)
    @NotNull
    private String email;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @NotNull
    @JoinColumn(name="company_id")
    private Company company;

    private String type;

    private int zipCode;

    @Column(name = "address")
    @NotNull
    private String address;

    private String state;





}
