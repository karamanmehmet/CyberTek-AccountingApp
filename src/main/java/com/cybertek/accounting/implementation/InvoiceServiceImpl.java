package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.enums.InvoiceStatus;
import com.cybertek.accounting.enums.InvoiceType;
import com.cybertek.accounting.exception.ExistentInvoiceException;
import com.cybertek.accounting.exception.InvoiceNotFoundException;
import com.cybertek.accounting.mapper.InvoiceMapper;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.repository.InvoiceRepository;
import com.cybertek.accounting.service.CompanyService;
import com.cybertek.accounting.service.InvoiceNumberService;
import com.cybertek.accounting.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository repository;
    private final MapperGeneric mapper;
    private final InvoiceNumberService invoiceNumberService;

    @Override
    public InvoiceDto create(InvoiceDto invoice) throws ExistentInvoiceException, Exception {

        Invoice foundInvoice = repository.findByInvoiceNo(invoice.getInvoiceNo());

        if (foundInvoice != null) throw new ExistentInvoiceException("This exception already exists");

        Invoice createdInvoice = mapper.convert(invoice,new Invoice());
        createdInvoice.setInvoiceNo(invoiceNumberService.create(invoice.getCompany()));

        repository.saveAndFlush(createdInvoice);

        invoice.setInvoiceNo(createdInvoice.getInvoiceNo());

        return invoice;
    }

    @Override
    public InvoiceDto update(InvoiceDto invoice) throws InvoiceNotFoundException {

        Invoice foundInvoice = repository.findByInvoiceNo(invoice.getInvoiceNo());

        if (foundInvoice == null) throw new InvoiceNotFoundException("This invoice does not exist");

        repository.saveAndFlush(mapper.convert(invoice,new Invoice()));

        return invoice;
    }

    @Override
    public boolean delete(InvoiceDto invoice) throws InvoiceNotFoundException {

        Invoice foundInvoice = repository.findByInvoiceNo(invoice.getInvoiceNo());

        if (foundInvoice == null) throw new InvoiceNotFoundException("This invoice does not exist");

        foundInvoice.setEnabled(false);
        foundInvoice.setInvoiceNo(invoice.getInvoiceNo() + "-" + foundInvoice.getId());

        repository.saveAndFlush(foundInvoice);

        return !foundInvoice.isEnabled();
    }

    @Override
    public Invoice findById(long id) throws InvoiceNotFoundException {

        return repository.findById(id).orElseThrow(() -> new InvoiceNotFoundException("Invoice with this id can not be found!"));
    }

    @Override
    public InvoiceDto findByIdDto(long id) throws InvoiceNotFoundException {

        Invoice invoice = repository.findById(id)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice with this id can not be found!"));

        return mapper.convert(invoice,new InvoiceDto());

    }

    @Override
    public InvoiceDto findByInvoiceNo(String invoiceNo) {
        Invoice invoice = repository.findByInvoiceNo(invoiceNo);
        return mapper.convert(invoice,new InvoiceDto());
    }

    @Override
    public List<InvoiceDto> findFirst3ByCompanyOrderByInvoiceDateAsc(CompanyDto company) {

        return repository.findFirst3ByCompanyOrderByInvoiceDateAsc(mapper.convert(company,new Company())).stream()
                .map(invoice -> mapper.convert(invoice,new InvoiceDto()))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDto> findFirst3ByCompanyOrderByInvoiceDateDesc(CompanyDto company) {

        return repository.findFirst3ByCompanyOrderByInvoiceDateDesc(mapper.convert(company,new Company())).stream()
                .map(invoice -> mapper.convert(invoice,new InvoiceDto()))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDto> findAllByCompanyAndInvoiceType(CompanyDto company, InvoiceType invoiceType) {

        return repository.findAllByCompanyAndInvoiceType(mapper.convert(company,new Company()), invoiceType).stream()
                .map(invoice -> mapper.convert(invoice,new InvoiceDto()))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDto> findAllByCompanyAndInvoiceStatus(CompanyDto company, InvoiceStatus invoiceStatus) {

        return repository.findAllByCompanyAndInvoiceStatus(mapper.convert(company,new Company()), invoiceStatus).stream()
                .map(invoice -> mapper.convert(invoice,new InvoiceDto()))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDto> findAllByCompanyAndInvoiceTypeAndInvoiceStatus(CompanyDto company, InvoiceType invoiceType, InvoiceStatus invoiceStatus) {
        return repository.findAllByCompanyAndInvoiceTypeAndInvoiceStatus(mapper.convert(company,new Company()), invoiceType, invoiceStatus).stream()
                .map(invoice -> mapper.convert(invoice,new InvoiceDto()))
                .collect(Collectors.toList());
    }
}
