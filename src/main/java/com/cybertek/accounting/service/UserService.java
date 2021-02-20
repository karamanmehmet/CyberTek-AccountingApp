package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.UserDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.User;

import java.util.List;

public interface UserService {


    UserDto create(UserDto userDto);

    List<UserDto> findAll();

    UserDto update(UserDto userDto);

    void delete(UserDto userDto);

    List<UserDto> findByCompanyAndEnabled(CompanyDto companyDto, Boolean enabled);
}
