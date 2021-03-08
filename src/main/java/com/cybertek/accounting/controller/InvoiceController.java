package com.cybertek.accounting.controller;

import com.cybertek.accounting.dto.*;
import com.cybertek.accounting.enums.ClientVendorType;
import com.cybertek.accounting.enums.InvoiceStatus;
import com.cybertek.accounting.enums.InvoiceType;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.InvoiceAlreadyExistsException;
import com.cybertek.accounting.exception.InvoiceNotFoundException;
import com.cybertek.accounting.exception.InvoiceProductNotFoundException;
import com.cybertek.accounting.service.*;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final CompanyService companyService;
    private final InvoiceProductService invoiceProductService;
    private final ProductService productService;
    private final ClientVendorService clientVendorService;

    @GetMapping("/purchaseList")
    public String getPurchaseInvoices(Model model) {

        //TODO: Change the static values below.

        try {
            List<InvoiceDto> purchaseInvoices = invoiceService.findAllByCompanyAndInvoiceTypeAndInvoiceStatus(companyService.findByEmail("karaman@crustycloud.com"), InvoiceType.PURCHASE, InvoiceStatus.OPEN);
            model.addAttribute("purchaseInvoices", purchaseInvoices);
        } catch (InvoiceNotFoundException | InvoiceProductNotFoundException | CompanyNotFoundException e) {
            e.printStackTrace();
        }

        return "/invoice/purchase-invoice-list";

    }

    @GetMapping("/purchaseCreate")
    public String createPurchaseInvoice(Model model) {

        return "/invoice/purchase-invoice-add";

    }

    @GetMapping("/purchaseAddItem")
    public String addItem(Model model) {

        return "/invoice/purchase-invoice-add";

    }

}
