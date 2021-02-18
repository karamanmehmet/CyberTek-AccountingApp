package com.cybertek.accounting.dto;

import com.cybertek.accounting.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private long id;
    private String description;
    private CompanyDto company;
    private Status status;

}
