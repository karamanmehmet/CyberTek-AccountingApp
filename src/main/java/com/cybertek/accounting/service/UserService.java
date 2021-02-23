package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.UserDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.User;

import java.util.List;

public interface UserService {


    UserDto create(UserDto userDto) throws Exception;

    List<UserDto> findAll();

    UserDto update(UserDto userDto) throws Exception;

    void delete(UserDto userDto) throws Exception;

    List<UserDto> findByCompanyAndEnabled(CompanyDto companyDto, Boolean enabled);
}
