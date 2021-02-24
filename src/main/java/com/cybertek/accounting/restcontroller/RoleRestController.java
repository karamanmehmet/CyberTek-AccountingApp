package com.cybertek.accounting.restcontroller;

import com.cybertek.accounting.dto.RoleDto;
import com.cybertek.accounting.entity.Role;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.service.RoleService;
import com.cybertek.accounting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
