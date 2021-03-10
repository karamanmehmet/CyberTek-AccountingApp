package com.cybertek.accounting.controller;

import com.cybertek.accounting.dto.InvoiceDto;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

        try {
            List<InvoiceDto> purchaseInvoices = invoiceService.findAllByInvoiceType(InvoiceType.PURCHASE);
            model.addAttribute("purchaseInvoices", purchaseInvoices);
        } catch (InvoiceNotFoundException | InvoiceProductNotFoundException | CompanyNotFoundException e) {
            e.printStackTrace();
        }

        return "/invoice/purchase-invoice-list";
    }

    @GetMapping("/purchaseCreate")
    public String createPurchaseInvoice(Model model) {
        model.addAttribute("invoice", new InvoiceDto());
        model.addAttribute("localDate", LocalDate.now());
        model.addAttribute("vendors", clientVendorService.findAllByType(ClientVendorType.VENDOR));
        return "/invoice/purchase-invoice-add";

    }

    @PostMapping("/purchaseCreate")
    public String insertPurchaseInvoice(@ModelAttribute InvoiceDto invoiceDto, @RequestParam(value="action", required=true) String action) {
        if (action.equals("save")) {
            try {
                invoiceDto.setInvoiceType(InvoiceType.PURCHASE);
                invoiceService.create(invoiceDto);
            } catch (InvoiceAlreadyExistsException | CompanyNotFoundException | InvoiceNotFoundException | InvoiceProductNotFoundException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/invoice/purchaseList";
    }

    @GetMapping("/purchaseAddItem/{invoiceNo}")
    public String addItem(@PathVariable("invoiceNo") String invoiceNo, Model model) {
        try {
            model.addAttribute("invoice", invoiceService.findByInvoiceNo(invoiceNo));
        } catch (InvoiceNotFoundException | InvoiceProductNotFoundException | CompanyNotFoundException e) {
            e.printStackTrace();
        }
        return "/invoice/purchase-invoice-add-line-item";
    }

}
