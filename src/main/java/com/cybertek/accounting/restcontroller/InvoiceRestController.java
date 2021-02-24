package com.cybertek.accounting.restcontroller;


import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.service.CompanyService;
import com.cybertek.accounting.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invoice")
public class InvoiceRestController {

    private final InvoiceService invoiceService;
    private final CompanyService companyService;


    @GetMapping("/{invoiceNo}")
    public InvoiceDto getInvoiceNumber(@PathVariable String invoiceNo) {

        return invoiceService.findByInvoiceNo(invoiceNo);
    }

    @PostMapping
    public InvoiceDto createInvoice(@RequestBody InvoiceDto invoiceDto) throws Exception {
      return invoiceService.create(invoiceDto);
    }

    @PutMapping("/update")
    public InvoiceDto updateInvoice(@RequestBody InvoiceDto invoiceDto) throws Exception {
        return invoiceService.update(invoiceDto);
    }

    @DeleteMapping("/{id}")
    public boolean deleteInvoice(@PathVariable("id") Long id) throws Exception {
        return invoiceService.delete(invoiceService.findByIdDto(id));
    }

//    @GetMapping("/first3ByCompanyAsc/{email}")
//    public List<Invoice> returnFirst3AscByCompany(@PathVariable("email") String email) throws Exception {
//
//        CompanyDto foundCompany = companyService.findByEmail(email);
//
//        return invoiceService.findFirst3ByCompanyOrderByInvoiceDateAsc(foundCompany);
//    }
//
//    @GetMapping("/first3ByCompanyDesc/{email}")
//    public List<Invoice> returnFirst3AscByCompany(@PathVariable("email") String email) throws Exception {
//
//        CompanyDto foundCompany = companyService.findByEmail(email);
//
//        return invoiceService.findFirst3ByCompanyOrderByInvoiceDateDesc(foundCompany);
//    }

    //TODO not sure on how to continue from findAllByCompanyAndInvoiceType onwards


}
