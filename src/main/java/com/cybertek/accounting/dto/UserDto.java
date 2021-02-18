package com.cybertek.accounting.dto;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String firstname;
    private String lastname;
    private String password;
    private boolean active;
    private String phone;
    private String email;

    private CompanyDto companyDto;

    private Set<RoleDto> roleDtos = new HashSet<>();
}
