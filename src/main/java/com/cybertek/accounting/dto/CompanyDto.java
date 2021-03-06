package com.cybertek.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private long id;
    private String title;
    private String address1;
    private String address2;
    private String state;
    private String zip;
    private String representative;
    private String phone;
    private String email;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate establishmentDate;
    private boolean enabled;

}
