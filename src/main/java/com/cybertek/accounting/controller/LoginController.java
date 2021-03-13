package com.cybertek.accounting.controller;

import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.InvoiceNotFoundException;
import com.cybertek.accounting.exception.InvoiceProductNotFoundException;
import com.cybertek.accounting.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {


    @RequestMapping(value = {"/","/login"})
    public String login(){

        return "login";
    }




}
