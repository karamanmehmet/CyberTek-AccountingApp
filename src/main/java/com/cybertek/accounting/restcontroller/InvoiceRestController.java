package com.cybertek.accounting.restcontroller;


import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.enums.InvoiceStatus;
import com.cybertek.accounting.enums.InvoiceType;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.InvoiceAlreadyExistsException;
import com.cybertek.accounting.exception.InvoiceNotFoundException;
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
    public InvoiceDto createInvoice(@RequestBody InvoiceDto invoiceDto) throws InvoiceAlreadyExistsException, Exception {
      return invoiceService.create(invoiceDto);
    }

    @PutMapping
    public InvoiceDto updateInvoice(@RequestBody InvoiceDto invoiceDto) throws InvoiceNotFoundException {
        return invoiceService.update(invoiceDto);
    }

    @DeleteMapping
    public boolean deleteInvoice(@RequestBody InvoiceDto invoiceDto) throws InvoiceNotFoundException {
        return invoiceService.delete(invoiceDto);
    }

    @GetMapping("/first3ByCompanyAsc/{companyEmail}")
    public List<InvoiceDto> returnFirst3AscByCompany(@PathVariable("companyEmail") String companyEmail) throws InvoiceNotFoundException, CompanyNotFoundException {

        CompanyDto foundCompany = companyService.findByEmail(companyEmail);

        return invoiceService.findFirst3ByCompanyOrderByInvoiceDateAsc(foundCompany);
    }

    @GetMapping("/first3ByCompanyDesc/{companyEmail}")
    public List<InvoiceDto> returnFirst3DescByCompany(@PathVariable String companyEmail) throws InvoiceNotFoundException, CompanyNotFoundException {

        CompanyDto foundCompany = companyService.findByEmail(companyEmail);

        return invoiceService.findFirst3ByCompanyOrderByInvoiceDateDesc(foundCompany);
    }

    @GetMapping("/{companyEmail}/invType/{invoiceType}")
    public List<InvoiceDto> returnAllByCompanyAndInvoiceType(@PathVariable String companyEmail, @PathVariable InvoiceType invoiceType) throws CompanyNotFoundException {

        CompanyDto company = companyService.findByEmail(companyEmail);

        return invoiceService.findAllByCompanyAndInvoiceType(company,invoiceType);
    }

    @GetMapping("/{companyEmail}/invStatus/{invoiceStatus}")
    public List<InvoiceDto> returnAllByCompanyAndInvoiceStatus(@PathVariable String companyEmail, @PathVariable  InvoiceStatus invoiceStatus) throws CompanyNotFoundException {

        CompanyDto company = companyService.findByEmail(companyEmail);

        return invoiceService.findAllByCompanyAndInvoiceStatus(company,invoiceStatus);
    }

    @GetMapping("/{companyEmail}/{invoiceType}/{invoiceStatus}")
    public List<InvoiceDto> returnAllByCompanyAndInvoiceType(@PathVariable String companyEmail, @PathVariable InvoiceType invoiceType, @PathVariable InvoiceStatus invoiceStatus) throws CompanyNotFoundException {

        CompanyDto company = companyService.findByEmail(companyEmail);

        return invoiceService.findAllByCompanyAndInvoiceTypeAndInvoiceStatus(company,invoiceType,invoiceStatus);
    }




}
