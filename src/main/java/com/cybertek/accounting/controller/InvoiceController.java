package com.cybertek.accounting.controller;

import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.dto.InvoiceMonetaryDetailDto;
import com.cybertek.accounting.enums.InvoiceType;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.service.CompanyService;
import com.cybertek.accounting.service.InvoiceService;
import com.cybertek.accounting.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final CompanyService companyService;
    private final PaymentService paymentService;

    @GetMapping("/purchases")
    public String getPurchaseInvoices(Model model) throws CompanyNotFoundException {

        List<InvoiceDto> purchaseInvoices = invoiceService.findAllByCompanyAndInvoiceType(companyService.findByEmail("karaman@crustycloud.com"), InvoiceType.PURCHASE);

        // Put it in a service.
        List<InvoiceMonetaryDetailDto> payments = purchaseInvoices.stream().map(invoice -> {
            try {
                return paymentService.create(invoice);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

        model.addAttribute("purchaseInvoices", purchaseInvoices);
        model.addAttribute("payments", payments);

        // How to put 2 model attributes in the same table.

        return "/invoice/purchase-invoice-list";

    }

}
