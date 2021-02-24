package com.cybertek.accounting.restcontroller;

import com.cybertek.accounting.dto.*;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final MapperGeneric mapper;

    @GetMapping("/users")
    public List<UserDto> getAllProducts() {
        return userService.findAll();

    }


        @GetMapping("/{company}")
        public List<UserDto> getAllCompanyEnabled (@PathVariable CompanyDto company){
            return userService.findByCompanyAndEnabled(company,true);
        }

        @PostMapping("/create")
    public UserDto createUser(@RequestBody UserDto user) throws Exception {
        return mapper.convert(userService.create(user),new UserDto());
        }

}
