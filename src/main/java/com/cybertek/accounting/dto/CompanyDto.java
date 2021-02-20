package com.cybertek.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private String title;
    private String address1;
    private String address2;
    private String state;
    private String zip;
    private String representative;
    private String email;
    private LocalDate establishmentDate;

}
