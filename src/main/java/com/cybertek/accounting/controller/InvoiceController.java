package com.cybertek.accounting.controller;

import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.dto.InvoiceMonetaryDetailDto;
import com.cybertek.accounting.enums.InvoiceStatus;
import com.cybertek.accounting.enums.InvoiceType;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.service.CompanyService;
import com.cybertek.accounting.service.InvoiceService;
import com.cybertek.accounting.service.InvoiceMonetaryDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final CompanyService companyService;
    private final InvoiceMonetaryDetailService invoiceMonetaryDetailService;

    @GetMapping("/purchaseInvoice/list")
    public String getPurchaseInvoices(Model model) throws Exception {

        //TODO: Change the static values below.
        List<InvoiceDto> purchaseInvoices = invoiceService.findAllByCompanyAndInvoiceTypeAndInvoiceStatus(companyService.findByEmail("karaman@crustycloud.com"), InvoiceType.PURCHASE, InvoiceStatus.OPEN);

        model.addAttribute("purchaseInvoices", purchaseInvoices);


        return "/invoice/purchase-invoice-list";

    }

}
