package com.cybertek.accounting.dto;

import com.cybertek.accounting.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class SPTableDto {


    private Long id;

    private String companyName;

    private String phone;

    private String email;

    private CompanyDto company;

    private String type;

    private int zipCode;

    private String address;

    private String state;

    private Status status;

    public SPTableDto(Long id, String companyName, String phone, String email,CompanyDto company, String type, int zipCode, String address, String state) {
        this.id = id;
        this.companyName = companyName;
        this.phone = phone;
        this.email = email;
        this.company = company;
        this.type = type;
        this.zipCode = zipCode;
        this.address = address;
        this.state = state;
        this.status= Status.ACTIVE;   // it should be active as default
    }
}
