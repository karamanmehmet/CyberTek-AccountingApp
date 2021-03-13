package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.entity.InvoiceNumber;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.InvoiceAlreadyExistsException;
import com.cybertek.accounting.exception.InvoiceNumberNotFoundException;
import com.cybertek.accounting.repository.CompanyRepository;
import com.cybertek.accounting.repository.InvoiceNumberRepository;
import com.cybertek.accounting.repository.InvoiceRepository;
import com.cybertek.accounting.service.InvoiceNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceNumberServiceImpl implements InvoiceNumberService {

    private final InvoiceNumberRepository invoiceNumberRepository;
    private final CompanyRepository companyRepository;
    private final InvoiceRepository invoiceRepository;

    @Override
    public InvoiceNumber create(InvoiceDto invoiceDto, CompanyDto companyDto) throws CompanyNotFoundException, InvoiceAlreadyExistsException {

        Company foundCompany = companyRepository.findById(companyDto.getId()).orElseThrow(() -> new CompanyNotFoundException("No company found"));

        Invoice foundInvoice = invoiceRepository.findByInvoiceNoAndCompany(invoiceDto.getInvoiceNo(), foundCompany);

        if (foundInvoice != null) {
            throw new InvoiceAlreadyExistsException("An invoice with this invoice number is already exist.");
        }

        InvoiceNumber createdInvoiceNumber = generate(companyDto);

        invoiceNumberRepository.saveAndFlush(createdInvoiceNumber);

        return createdInvoiceNumber;

    }

    @Override
    public List<InvoiceNumber> findInvoiceNumberByCompanyAndYear(CompanyDto company, int year) throws CompanyNotFoundException, InvoiceNumberNotFoundException {

        Company foundCompany = companyRepository.findById(company.getId()).orElseThrow(() -> new CompanyNotFoundException("No company found"));
        List<InvoiceNumber> foundInvoiceNumberList = invoiceNumberRepository.findInvoiceNumberByCompanyAndYear(foundCompany, year);

        if (foundInvoiceNumberList == null) {
            throw new InvoiceNumberNotFoundException("No invoice number found");
        }

        return foundInvoiceNumberList;

    }

    @Override
    public List<InvoiceNumber> findInvoiceNumberByCompany(CompanyDto company) throws CompanyNotFoundException, InvoiceNumberNotFoundException {

        Company foundCompany = companyRepository.findById(company.getId()).orElseThrow(() -> new CompanyNotFoundException("No company found"));
        List<InvoiceNumber> foundInvoiceNumberList = invoiceNumberRepository.findInvoiceNumberByCompany(foundCompany);

        if (foundInvoiceNumberList == null) {
            throw new InvoiceNumberNotFoundException("No invoice number found");
        }

        return foundInvoiceNumberList;

    }

    @Override
    public InvoiceNumber findFirstByCompanyOrderByInvoiceNumberDesc(CompanyDto company) throws CompanyNotFoundException {

        Company foundCompany = companyRepository.findByEmail(company.getEmail()).orElseThrow(() -> new CompanyNotFoundException("No company found"));

        return invoiceNumberRepository.findFirstByCompanyOrderByInvoiceNumberDesc(foundCompany).orElse(null);

    }

    private InvoiceNumber generate(CompanyDto company) throws CompanyNotFoundException {

        InvoiceNumber lastInvoiceNumber = findFirstByCompanyOrderByInvoiceNumberDesc(company);

        Company foundCompany = companyRepository.findByEmail(company.getEmail()).orElseThrow(() -> new CompanyNotFoundException("No company found"));

        if (lastInvoiceNumber == null) {

            lastInvoiceNumber = new InvoiceNumber();

            lastInvoiceNumber.setInvoiceNumber(0);
            lastInvoiceNumber.setYear(LocalDate.now().getYear());
            lastInvoiceNumber.setCompany(foundCompany);

            return lastInvoiceNumber;

        }

        InvoiceNumber createdInvoiceNumber = new InvoiceNumber();

        createdInvoiceNumber.setInvoiceNumber(lastInvoiceNumber.getInvoiceNumber() + 1);
        createdInvoiceNumber.setYear(LocalDate.now().getYear());
        createdInvoiceNumber.setCompany(foundCompany);

        return createdInvoiceNumber;

    }

}
