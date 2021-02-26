package com.cybertek.accounting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {

    @RequestMapping
    public String login(){

        return "login";
    }



    @RequestMapping("/main")
    public String welcome(){
        return "main";
    }
}
