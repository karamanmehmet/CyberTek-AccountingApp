package com.cybertek.accounting.mapper;

import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.dto.SPTableDto;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.entity.SPTable;
import com.cybertek.accounting.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvoiceMapper {

    private final InvoiceRepository repository;
    private final CompanyMapper companyMapper;
    private final SPTableMapper spTableMapper;


    public Invoice convertToEntity(InvoiceDto dto) {

        return repository.findByInvoiceNo(dto.getInvoiceNo());

    }

    public InvoiceDto convertToDto(Invoice obj) {

        return new InvoiceDto(obj.getInvoiceNo(),
                obj.getInvoiceType(),
                obj.getInvoiceStatus(),
                obj.getInvoiceDate(),
                spTableMapper.convertToDto(obj.getSptable()),
                companyMapper.convertToDto(obj.getCompany()));

    }
}
