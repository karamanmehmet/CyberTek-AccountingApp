package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.UserDto;
import com.cybertek.accounting.exception.UserAlreadyExist;
import com.cybertek.accounting.exception.UserNotFound;

import java.util.List;

public interface UserService {


    UserDto create(UserDto userDto) throws UserAlreadyExist;

    List<UserDto> findAll() ;

    UserDto update(UserDto userDto) throws UserNotFound;

    void delete(UserDto userDto) throws Exception;

    List<UserDto> findByCompanyAndEnabled(CompanyDto companyDto, Boolean enabled);
}
