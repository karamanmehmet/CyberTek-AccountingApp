package com.cybertek.accounting.restcontroller;


import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invoice")
public class InvoiceRestController {

    private final InvoiceService service;


  @GetMapping("/{invoiceNo}")
    public InvoiceDto getInvoiceNumber(@PathVariable String invoiceNo){

      return  service.findByInvoiceNo(invoiceNo);
  }


}
