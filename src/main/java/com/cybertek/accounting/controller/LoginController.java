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
@RequiredArgsConstructor
public class LoginController {
    private final InvoiceService invoiceService;

    @RequestMapping(value = {"/","/login"})
    public String login(){

        return "login";
    }



    @RequestMapping("/main")
    public String welcome(Model model) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException {
        model.addAttribute("invoices",invoiceService.findFirst3ByCompanyOrderByInvoiceDateAsc());

        return "main";
    }
}
