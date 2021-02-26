package com.cybertek.accounting.controller;

import com.cybertek.accounting.dto.UserDto;
import com.cybertek.accounting.exception.AccountingAppException;
import com.cybertek.accounting.service.CompanyService;
import com.cybertek.accounting.service.RoleService;
import com.cybertek.accounting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    RoleService roleService;
    UserService userService;
    CompanyService companyService;

    public UserController(RoleService roleService, UserService userService, CompanyService companyService) {
        this.roleService = roleService;
        this.userService = userService;
        this.companyService = companyService;
    }

    @GetMapping("/user-add")
    public String createUser(Model model){
        model.addAttribute("user",new UserDto());
        model.addAttribute("roles",roleService.findAll());
        model.addAttribute("companies",companyService.findAll());
        return "user/user-add";
    }
    @PostMapping("/user-add")
    public String insertUser(UserDto user,Model model) throws AccountingAppException {
        userService.create(user);
        return "redirect:/user/user-add";
    }
    //


}

