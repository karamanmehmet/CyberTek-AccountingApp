package com.cybertek.accounting.controller;

import com.cybertek.accounting.dto.Rates;
import com.cybertek.accounting.exception.CategoryNotFoundException;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.InvoiceNotFoundException;
import com.cybertek.accounting.exception.InvoiceProductNotFoundException;
import com.cybertek.accounting.service.ExchangeRateService;
import com.cybertek.accounting.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final InvoiceService invoiceService;
    private final ExchangeRateService exchangeRateService;

    @GetMapping("/main")
    public String welcome(Model model) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException {
        Rates rates=exchangeRateService.exchangeRates();

        model.addAttribute("rates",rates);

        model.addAttribute("invoices",invoiceService.findFirst3ByCompanyOrderByInvoiceDateAsc());
        return "main";

    }
}
