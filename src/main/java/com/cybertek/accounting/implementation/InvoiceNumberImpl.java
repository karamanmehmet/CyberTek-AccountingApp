package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.InvoiceNumber;
import com.cybertek.accounting.repository.CompanyRepository;
import com.cybertek.accounting.repository.InvoiceNumberRepository;
import com.cybertek.accounting.service.InvoiceNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceNumberImpl implements InvoiceNumberService {

    private final InvoiceNumberRepository invoiceNumberRepository;
    private final CompanyRepository companyRepository;

    // Should we add invoice number to the invoice in the InvoiceImpl or here?
    public String create(Company company) throws Exception {
        return generateInvoiceNumber(company);
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

    private String generateInvoiceNumber(Company company) throws Exception {

        Company foundCompany = companyRepository.findById(company.getId()).orElseThrow(() -> new Exception("No company found"));
        List<InvoiceNumber> invoiceNumberByCompanyList = invoiceNumberRepository.findInvoiceNumberByCompany(foundCompany);

        int lastInvoiceNumber = invoiceNumberRepository.findLastInvoiceNumberByCompany(company).getInvoiceNumber();
        String generatedInvoiceNumber;

        if (invoiceNumberByCompanyList.size() == 0) {
            generatedInvoiceNumber = "000";
        }else {
            generatedInvoiceNumber = String.valueOf(lastInvoiceNumber + 1);

            while (generatedInvoiceNumber.length() < 3){
                generatedInvoiceNumber = "0".concat(generatedInvoiceNumber);
            }
        }

        String result = "INV-" + generatedInvoiceNumber;

        return result;

    }

}
