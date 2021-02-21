package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.enums.InvoiceStatus;
import com.cybertek.accounting.enums.InvoiceType;
import com.cybertek.accounting.mapper.InvoiceMapper;
import com.cybertek.accounting.repository.InvoiceRepository;
import com.cybertek.accounting.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository repository;
    private final InvoiceMapper mapper;

    @Override
    public InvoiceDto create(InvoiceDto invoice) {
        return null;
    }

    @Override
    public InvoiceDto update(InvoiceDto invoice) {
        return null;
    }

    @Override
    public boolean delete(InvoiceDto invoice) {
        return false;
    }

    @Override
    public Invoice findById(long id) {
        return null;
    }

    @Override
    public InvoiceDto findByIdDto(long id) {
        return null;
    }

    @Override
    public InvoiceDto findByInvoiceNo(String invoiceNo) {
        Invoice invoice = repository.findByInvoiceNo(invoiceNo);
        return mapper.convertToDto(invoice);
    }

    @Override
    public List<Invoice> findFirst3ByCompanyOrderByInvoiceDateAsc(Company company) {
        return null;
    }

    @Override
    public List<Invoice> findFirst3ByCompanyOrderByInvoiceDateDesc(Company company) {
        return null;
    }

    @Override
    public List<Invoice> findAllByCompanyAndInvoiceType(Company company, InvoiceType invoiceType) {
        return null;
    }

    @Override
    public List<Invoice> findAllByCompanyAndInvoiceStatus(Company company, InvoiceStatus invoiceStatus) {
        return null;
    }

    @Override
    public List<Invoice> findAllByCompanyAndInvoiceTypeAndInvoiceStatus(Company company, InvoiceType invoiceType, InvoiceStatus invoiceStatus) {
        return null;
    }
}
