package com.cybertek.accounting.controller;

import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.dto.InvoiceProductDto;
import com.cybertek.accounting.enums.ClientVendorType;
import com.cybertek.accounting.enums.InvoiceType;
import com.cybertek.accounting.exception.*;
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
    private final InvoiceProductService invoiceProductService;
    private final ProductService productService;
    private final ClientVendorService clientVendorService;

    @GetMapping("/{invoiceNo}")
    public String getInvoice(@PathVariable("invoiceNo") String invoiceNo, Model model) {
        try {
            model.addAttribute("invoice", invoiceService.findByInvoiceNo(invoiceNo));
            model.addAttribute("invoiceProductList", invoiceProductService.findByInvoice(invoiceService.findByInvoiceNo(invoiceNo)));
            //TODO Ask how to get the lastUpdateUser or insertUser properly and implement it.
//            model.addAttribute("authorizedPerson");
        } catch (InvoiceNotFoundException | InvoiceProductNotFoundException | CompanyNotFoundException e) {
            e.printStackTrace();
        }
        return "/invoice/invoice";
    }

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
    public String insertPurchaseInvoice(@ModelAttribute InvoiceDto invoiceDto, @RequestParam(value = "action", required = true) String action) {
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

    @PostMapping("/purchaseUpdate/{invoiceNo}")
    public String updatePurchaseInvoice(@PathVariable("invoiceNo") String invoiceNo, @RequestParam(value = "action", required = true) String action) {

        try {
            if (action.equals("approve")) {
                invoiceService.approve(invoiceNo);
            } else if (action.equals("delete")) {
                invoiceService.delete(invoiceNo);
            }
        } catch (InvoiceNotFoundException | InvoiceProductNotFoundException | CompanyNotFoundException e) {
            e.printStackTrace();
        }
        return "redirect:/invoice/purchaseList";
    }

    @GetMapping("/purchaseAddItem/{invoiceNo}")
    public String addPurchaseItem(@PathVariable("invoiceNo") String invoiceNo, Model model) {
        try {
            model.addAttribute("invoice", invoiceService.findByInvoiceNo(invoiceNo));
            model.addAttribute("invoiceProduct", new InvoiceProductDto());
            model.addAttribute("products", productService.findAll());
            model.addAttribute("invoiceProductList", invoiceProductService.findByInvoice(invoiceService.findByInvoiceNo(invoiceNo)));
            model.addAttribute("vendors", clientVendorService.findAllByType(ClientVendorType.VENDOR));
            model.addAttribute("invoiceDate", invoiceService.findByInvoiceNo(invoiceNo).getInvoiceDate());
        } catch (InvoiceNotFoundException | InvoiceProductNotFoundException | CompanyNotFoundException e) {
            e.printStackTrace();
        }
        return "/invoice/purchase-invoice-add-line-item";
    }

    @PostMapping("/purchaseAddItem/{invoiceNo}")
    public String insertPurchaseItem(@PathVariable("invoiceNo") String invoiceNo, @ModelAttribute("invoiceProduct") InvoiceProductDto invoiceProductDto) {
        try {
            invoiceProductDto.setInvoice(invoiceService.findByInvoiceNo(invoiceNo));
            invoiceProductService.create(invoiceProductDto);
        } catch (InvoiceProductNotFoundException | InvoiceNotFoundException | ProductNotFoundException | NotEnoughProductInStockException | CompanyNotFoundException e) {
            e.printStackTrace();
        }
        return "redirect:/invoice/purchaseAddItem/" + invoiceNo;
    }

    @PostMapping("/purchaseRemoveItem/{id}")
    public String removePurchaseItem(@PathVariable("id") long id, @ModelAttribute InvoiceProductDto invoiceProductDto) {
        try {
            invoiceProductService.delete(invoiceProductDto);
        } catch (InvoiceProductNotFoundException | InvoiceNotFoundException | NotEnoughProductInStockException | ProductNotFoundException | CompanyNotFoundException e) {
            e.printStackTrace();
        }
        return "redirect:/invoice/purchaseAddItem/" + invoiceProductDto.getInvoice().getInvoiceNo();
    }

    @GetMapping("/salesList")
    public String getSalesInvoices(Model model) {

        try {
            List<InvoiceDto> salesInvoices = invoiceService.findAllByInvoiceType(InvoiceType.SALES);
            model.addAttribute("salesInvoices", salesInvoices);
        } catch (InvoiceNotFoundException | InvoiceProductNotFoundException | CompanyNotFoundException e) {
            e.printStackTrace();
        }

        return "/invoice/sales-invoice-list";
    }

    @GetMapping("/salesCreate")
    public String createSalesInvoice(Model model) {
        model.addAttribute("invoice", new InvoiceDto());
        model.addAttribute("localDate", LocalDate.now());
        model.addAttribute("clients", clientVendorService.findAllByType(ClientVendorType.CLIENT));
        return "/invoice/sales-invoice-add";
    }

    @PostMapping("/salesCreate")
    public String insertSalesInvoice(@ModelAttribute InvoiceDto invoiceDto, @RequestParam(value = "action", required = true) String action) {
        if (action.equals("save")) {
            try {
                invoiceDto.setInvoiceType(InvoiceType.SALES);
                invoiceService.create(invoiceDto);
            } catch (InvoiceAlreadyExistsException | CompanyNotFoundException | InvoiceNotFoundException | InvoiceProductNotFoundException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/invoice/salesList";
    }

    @PostMapping("/salesUpdate/{invoiceNo}")
    public String updateSalesInvoice(@PathVariable("invoiceNo") String invoiceNo, @RequestParam(value = "action", required = true) String action) {

        try {
            if (action.equals("approve")) {
                invoiceService.approve(invoiceNo);
            } else if (action.equals("delete")) {
                invoiceService.delete(invoiceNo);
            } else if (action.equals("archive")) {
                invoiceService.archive(invoiceNo);
            }
        } catch (InvoiceNotFoundException | InvoiceProductNotFoundException | CompanyNotFoundException e) {
            e.printStackTrace();
        }
        return "redirect:/invoice/salesList";
    }

    @GetMapping("/salesAddItem/{invoiceNo}")
    public String addSalesItem(@PathVariable("invoiceNo") String invoiceNo, Model model) {
        try {
            model.addAttribute("invoice", invoiceService.findByInvoiceNo(invoiceNo));
            model.addAttribute("invoiceProduct", new InvoiceProductDto());
            model.addAttribute("products", productService.findAll());
            model.addAttribute("invoiceProductList", invoiceProductService.findByInvoice(invoiceService.findByInvoiceNo(invoiceNo)));
            model.addAttribute("clients", clientVendorService.findAllByType(ClientVendorType.CLIENT));
            model.addAttribute("invoiceDate", invoiceService.findByInvoiceNo(invoiceNo).getInvoiceDate());
        } catch (InvoiceNotFoundException | InvoiceProductNotFoundException | CompanyNotFoundException e) {
            e.printStackTrace();
        }
        return "/invoice/sales-invoice-add-line-item";
    }

    @PostMapping("/salesAddItem/{invoiceNo}")
    public String insertSalesItem(@PathVariable("invoiceNo") String invoiceNo, @ModelAttribute("invoiceProduct") InvoiceProductDto invoiceProductDto) {
        try {
            invoiceProductDto.setInvoice(invoiceService.findByInvoiceNo(invoiceNo));
            invoiceProductService.create(invoiceProductDto);
        } catch (InvoiceProductNotFoundException | InvoiceNotFoundException | ProductNotFoundException | NotEnoughProductInStockException | CompanyNotFoundException e) {
            e.printStackTrace();
        }
        return "redirect:/invoice/salesAddItem/" + invoiceNo;
    }

    @PostMapping("/salesRemoveItem/{id}")
    public String removeSalesItem(@PathVariable("id") long id, @ModelAttribute InvoiceProductDto invoiceProductDto) {
        try {
            invoiceProductService.delete(invoiceProductDto);
        } catch (InvoiceProductNotFoundException | InvoiceNotFoundException | NotEnoughProductInStockException | ProductNotFoundException | CompanyNotFoundException e) {
            e.printStackTrace();
        }
        return "redirect:/invoice/salesAddItem/" + invoiceProductDto.getInvoice().getInvoiceNo();
    }

}
