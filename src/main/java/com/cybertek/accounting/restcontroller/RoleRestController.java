package com.cybertek.accounting.restcontroller;

import com.cybertek.accounting.dto.RoleDto;
import com.cybertek.accounting.dto.UserDto;
import com.cybertek.accounting.entity.Role;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.service.RoleService;
import com.cybertek.accounting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleRestController {
    private final RoleService roleService;
    private final MapperGeneric mapper;

    @GetMapping("/{name}")
    RoleDto findByName(@PathVariable("name") String name){
        return roleService.findByName(name);
    }

    @PutMapping("/update")
    public RoleDto updateRole(@RequestBody RoleDto roleDto) throws Exception {
        return  roleService.update(roleDto);
    }

    @PostMapping("/create")
    public RoleDto createRole(@RequestBody RoleDto role) throws Exception {
        return roleService.create(role);
    }



}
