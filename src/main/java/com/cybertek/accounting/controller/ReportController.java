package com.cybertek.accounting.controller;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.InvoiceProductDto;
import com.cybertek.accounting.dto.ProductDto;
import com.cybertek.accounting.dto.Rates;
import com.cybertek.accounting.entity.InvoiceProduct;
import com.cybertek.accounting.entity.Product;
import com.cybertek.accounting.enums.InvoiceStatus;
import com.cybertek.accounting.enums.InvoiceType;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.InvoiceNotFoundException;
import com.cybertek.accounting.exception.InvoiceProductNotFoundException;
import com.cybertek.accounting.service.InvoiceProductService;
import com.cybertek.accounting.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ProductService productService;
    private final HttpSession httpSession;
    private final InvoiceProductService invoiceProductService;

    @RequestMapping("/stock")
    public String stock(Model model) throws CompanyNotFoundException {

        CompanyDto company = (CompanyDto)httpSession.getAttribute("company");

        List<ProductDto> list=productService.findByCompany(company);

        model.addAttribute("products",list);
        return "/report/stock";
    }

    @RequestMapping("/profit-loss")
    public String profit_loss(Model model)  {

        CompanyDto company = (CompanyDto)httpSession.getAttribute("company");

        List<InvoiceProductDto> salesList = invoiceProductService.findByInvoiceStatusAndInvoiceTypeAndCompany(company, InvoiceType.SALES, InvoiceStatus.OPEN);

        List<InvoiceProductDto> purchaseList = invoiceProductService.findByInvoiceStatusAndInvoiceTypeAndCompany(company, InvoiceType.PURCHASE, InvoiceStatus.OPEN);

        //get all purchased
        Map<ProductDto, Queue<InvoiceProductDto>> stockMap= new HashMap<>();

        for (InvoiceProductDto purchase:purchaseList ) {
            Queue<InvoiceProductDto> inStock = stockMap.getOrDefault(purchase.getProduct(),new LinkedList<>());
            inStock.add(purchase);
            stockMap.put(purchase.getProduct(),inStock);
        }






        return "/report/profit-loss";
    }
}
