package com.cybertek.accounting.mapper;

import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvoiceMapper {

    private final InvoiceRepository repository;
    private final CompanyMapper companyMapper;
    private final ClientVendorMapper clientVendorMapper;


    public Invoice convertToEntity(InvoiceDto dto) {

        return repository.findByInvoiceNo(dto.getInvoiceNo());

    }

//    public InvoiceDto convertToDto(Invoice obj) {
//
//        return new InvoiceDto(obj.getInvoiceNo(),
//                obj.getInvoiceType(),
//                obj.getInvoiceStatus(),
//                obj.getInvoiceDate(),
//                clientVendorMapper.convertToDto(obj.getClientVendor()),
//                companyMapper.convertToDto(obj.getCompany()));
//
//    }
}