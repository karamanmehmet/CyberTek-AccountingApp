package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.RoleDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.Role;

import java.util.List;

public interface RoleService {

    Role create(RoleDto role);

    List<RoleDto> readAll();

    Role update(RoleDto role);

    void delete(RoleDto role);


    List<RoleDto> readByName(String name);

}
