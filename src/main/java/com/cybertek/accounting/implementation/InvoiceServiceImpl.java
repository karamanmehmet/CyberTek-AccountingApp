package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.enums.InvoiceStatus;
import com.cybertek.accounting.enums.InvoiceType;
import com.cybertek.accounting.mapper.InvoiceMapper;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.repository.InvoiceRepository;
import com.cybertek.accounting.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository repository;
    private final MapperGeneric mapper;

    @Override
    public InvoiceDto create(InvoiceDto invoice) throws Exception {

        Invoice foundInvoice = repository.findByInvoiceNo(invoice.getInvoiceNo());

        if (foundInvoice != null) throw new Exception("This exception already exists");

        repository.saveAndFlush(mapper.convert(invoice,new Invoice()));

        return invoice;
    }

    @Override
    public InvoiceDto update(InvoiceDto invoice) throws Exception {

        Invoice foundInvoice = repository.findByInvoiceNo(invoice.getInvoiceNo());

        if (foundInvoice == null) throw new Exception("This invoice does not exist");

        repository.saveAndFlush(mapper.convert(invoice,new Invoice()));

        return invoice;
    }

    @Override
    public boolean delete(InvoiceDto invoice) throws Exception {

        Invoice foundInvoice = repository.findByInvoiceNo(invoice.getInvoiceNo());

        if (foundInvoice == null) throw new Exception("This invoice does not exist");

        foundInvoice.setEnabled(false);
        foundInvoice.setInvoiceNo(invoice.getInvoiceNo() + "-" + foundInvoice.getId());

        repository.saveAndFlush(foundInvoice);

        return !foundInvoice.isEnabled();
    }

    @Override
    public Invoice findById(long id) throws Exception {

        return repository.findById(id).orElseThrow(() -> new Exception("Invoice with this id can not be found!"));
    }

    @Override
    public InvoiceDto findByIdDto(long id) throws Exception {

        Invoice invoice = repository.findById(id)
                .orElseThrow(() -> new Exception("Invoice with this id can not be found!"));

        return mapper.convert(invoice,new InvoiceDto());

    }

    @Override
    public InvoiceDto findByInvoiceNo(String invoiceNo) {
        Invoice invoice = repository.findByInvoiceNo(invoiceNo);
        return mapper.convert(invoice,new InvoiceDto());
    }

    @Override
    public List<Invoice> findFirst3ByCompanyOrderByInvoiceDateAsc(Company company) {

        //TODO should return type be DTO?
        return repository.findFirst3ByCompanyOrderByInvoiceDateAsc(company);
    }

    @Override
    public List<Invoice> findFirst3ByCompanyOrderByInvoiceDateDesc(Company company) {
        //TODO should return type be DTO?
        return repository.findFirst3ByCompanyOrderByInvoiceDateDesc(company);
    }

    @Override
    public List<Invoice> findAllByCompanyAndInvoiceType(Company company, InvoiceType invoiceType) {

        return repository.findAllByCompanyAndInvoiceType(company, invoiceType);
    }

    @Override
    public List<Invoice> findAllByCompanyAndInvoiceStatus(Company company, InvoiceStatus invoiceStatus) {

        return repository.findAllByCompanyAndInvoiceStatus(company, invoiceStatus);
    }

    @Override
    public List<Invoice> findAllByCompanyAndInvoiceTypeAndInvoiceStatus(Company company, InvoiceType invoiceType, InvoiceStatus invoiceStatus) {
        return repository.findAllByCompanyAndInvoiceTypeAndInvoiceStatus(company, invoiceType, invoiceStatus);
    }
}
