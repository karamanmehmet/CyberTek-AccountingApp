package com.cybertek.accounting.restcontroller;

import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.service.RoleService;
import com.cybertek.accounting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleRestController {
    private final RoleService roleService;
    private final MapperGeneric mapper;

}
