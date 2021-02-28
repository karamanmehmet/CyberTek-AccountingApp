package com.cybertek.accounting.entity;

import com.cybertek.accounting.enums.ClientVendorType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@ToString
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientVendor extends BaseEntity {
    // TODO if we add unique for companyName and Email we ca not create VENDOR / CLIENT with same credentials
    // I checked it implementation
    //@Column(unique = true)
    @NotNull
    private String companyName;

    private String phone;

    //@Column(unique = true)
    @NotNull
    private String email;


    @ManyToOne
    @NotNull
    @JoinColumn(name="company_id")
    private Company company;

    @Enumerated(EnumType.STRING)
    private ClientVendorType type;

    private String zipCode;

    @Column(name = "address")
    @NotNull
    private String address;

    private String state;

    private boolean enabled=true;








}
