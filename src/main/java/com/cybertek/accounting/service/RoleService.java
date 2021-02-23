package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.RoleDto;


import java.util.List;

public interface RoleService {

    RoleDto create(RoleDto role) throws Exception;

    List<RoleDto> findAll();

    RoleDto update(RoleDto role);

    void delete(RoleDto role);

    RoleDto findByName(String name);

}
