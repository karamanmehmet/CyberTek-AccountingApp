package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.entity.InvoiceNumber;
import com.cybertek.accounting.repository.CompanyRepository;
import com.cybertek.accounting.repository.InvoiceNumberRepository;
import com.cybertek.accounting.repository.InvoiceRepository;
import com.cybertek.accounting.service.InvoiceNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceNumberServiceImpl implements InvoiceNumberService {

    private final InvoiceNumberRepository invoiceNumberRepository;
    private final CompanyRepository companyRepository;
    private final InvoiceRepository invoiceRepository;

    @Override
    public String create(CompanyDto companyDto) throws Exception {

        String generatedInvoiceNumber;

        Company foundCompany = companyRepository.findById(companyDto.getId()).orElseThrow(() -> new Exception("No company found"));
        List<InvoiceNumber> invoiceNumberList = invoiceNumberRepository.findInvoiceNumberByCompany(foundCompany);

        InvoiceNumber invoiceNumber = invoiceNumberRepository.findLastInvoiceNumberByCompanyId(foundCompany.getId());

        Invoice invoice = new Invoice();

        int lastInvoiceNumber = invoiceNumber.getInvoiceNumber();

        if (invoiceNumberList.size() == 0) {
            generatedInvoiceNumber = generateInvoiceNumber((-1));
            invoiceNumber.setCompany(foundCompany);

            invoice = invoiceRepository.findByInvoiceNo(generatedInvoiceNumber);

            invoiceNumber.setYear(invoice.getInvoiceDate().getYear());

        } else {
            generatedInvoiceNumber = generateInvoiceNumber(lastInvoiceNumber);
        }

        return generatedInvoiceNumber;
    }

    @Override
    public List<String> findInvoiceNumberByCompanyAndYear(CompanyDto company, int year) throws Exception {

        Company foundCompany = companyRepository.findById(company.getId()).orElseThrow(() -> new Exception("No company found"));
        List<InvoiceNumber> foundInvoiceNumberList = invoiceNumberRepository.findInvoiceNumberByCompanyAndYear(foundCompany, year);

        if (foundInvoiceNumberList == null) {
            throw new Exception("No invoice number found");
        }

        return foundInvoiceNumberList.stream().map(invoiceNumber -> String.valueOf(invoiceNumber.getInvoiceNumber())).collect(Collectors.toList());

    }

    @Override
    public List<String> findInvoiceNumberByCompany(Company company) throws Exception {

        Company foundCompany = companyRepository.findById(company.getId()).orElseThrow(() -> new Exception("No company found"));
        List<InvoiceNumber> foundInvoiceNumberList = invoiceNumberRepository.findInvoiceNumberByCompany(foundCompany);

        if (foundInvoiceNumberList == null) {
            throw new Exception("No invoice number found");
        }

        return foundInvoiceNumberList.stream().map(invoiceNumber -> String.valueOf(invoiceNumber.getInvoiceNumber())).collect(Collectors.toList());

    }

    private String generateInvoiceNumber(int lastInvoiceNumber) {

        String generatedInvoiceNumber;

        if (lastInvoiceNumber == -1) {
            generatedInvoiceNumber = "000";
        } else {
            generatedInvoiceNumber = String.valueOf(lastInvoiceNumber + 1);

            while (generatedInvoiceNumber.length() < 3) {
                generatedInvoiceNumber = "0".concat(generatedInvoiceNumber);
            }

        }

        String result = "INV-" + generatedInvoiceNumber;

        return result;

    }

}
