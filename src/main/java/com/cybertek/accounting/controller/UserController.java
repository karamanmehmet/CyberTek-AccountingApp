package com.cybertek.accounting.controller;

import com.cybertek.accounting.dto.UserDto;
import com.cybertek.accounting.exception.UserAlreadyExist;
import com.cybertek.accounting.exception.UserNotFound;
import com.cybertek.accounting.service.CompanyService;
import com.cybertek.accounting.service.RoleService;
import com.cybertek.accounting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/add")
    public String createUser(Model model){
        model.addAttribute("user",new UserDto());
        model.addAttribute("roles",roleService.findAll());
        model.addAttribute("companies",companyService.findAll());
        return "user/user-add";
    }
    @PostMapping("/add")
    public String insertUser(@ModelAttribute UserDto user, Model model) throws  UserAlreadyExist {

        userService.create(user);
        return "redirect:/user/add";
    }
    //
    @GetMapping("/list")
    public String listUsers(Model model) throws UserNotFound {
        model.addAttribute("users",userService.findAll());
        return "user/user-list";
    }

    @GetMapping("/update/{userEmail}")
    public String editUser(Model model, @PathVariable String userEmail){

        model.addAttribute("user",userService.findByEmail(userEmail));
        model.addAttribute("roles",roleService.findAll());
        model.addAttribute("companies",companyService.findAll());

        return "/user/user-update";
    }

    @PostMapping("/update/{userEmail}")
    public String updateUser(@PathVariable String userEmail, @ModelAttribute("user") UserDto userDto,
                             @RequestParam(value="action", required = true) String action) throws Exception {

        if (action.equals("save")) userService.update(userDto);

        if (action.equals("delete")) userService.delete(userService.findByEmail(userEmail));

        return "redirect:/user/list";
    }

}

