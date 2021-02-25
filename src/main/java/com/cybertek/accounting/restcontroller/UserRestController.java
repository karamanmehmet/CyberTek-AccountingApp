package com.cybertek.accounting.restcontroller;

import com.cybertek.accounting.dto.*;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.service.CompanyService;
import com.cybertek.accounting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final CompanyService companyService;
    private final MapperGeneric mapper;

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return userService.findAll();

    }


        @GetMapping("/company/enable/{email}")
        public List<UserDto> getAllCompanyEnabled (@PathVariable("email")String email) throws Exception {
            return userService.findByCompanyAndEnabled(companyService.findByEmail(email),true);
        }

        @PostMapping("/create")
        public UserDto createUser(@RequestBody UserDto user) throws Exception {
        return userService.create(user);
        }

        @PutMapping("/update")
        public UserDto updateProduct(@RequestBody UserDto userDto) throws Exception {
            return  userService.update(userDto);
        }

//    @DeleteMapping("delete/{email}")
//    public void deleteUser(@PathVariable("email") String email) throws Exception {
//        return userService.delete(userService.findByEmail(email));
//    }
}
