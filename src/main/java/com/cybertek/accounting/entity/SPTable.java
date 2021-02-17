package com.cybertek.accounting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    private String type;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="company_id")
    private Company company;



}
