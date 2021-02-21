package com.cybertek.accounting.dto;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.enums.ClientVendorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class SPTableDto {


    private Long id;

    private String companyName;

    private String phone;

    private String email;

    private CompanyDto company;

    private String zipCode;

    private String address;

    private String state;

    private ClientVendorType type;

    private boolean enabled;


}
