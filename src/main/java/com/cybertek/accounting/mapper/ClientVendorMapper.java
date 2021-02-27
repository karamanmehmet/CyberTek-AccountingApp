//package com.cybertek.accounting.mapper;
//
//import com.cybertek.accounting.dto.ClientVendorDto;
//import com.cybertek.accounting.entity.ClientVendor;
//import com.cybertek.accounting.repository.ClientVendorRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class ClientVendorMapper {
//
//    private final ClientVendorRepository repository;
//    private final CompanyMapper companyMapper;
//
//    public ClientVendor convertToEntity(ClientVendorDto dto) {
//
//        return repository.findById(dto.getId()).get();
//
//    }
//
//    public ClientVendorDto convertToDto(ClientVendor obj) {
//
//        return new ClientVendorDto(obj.getId(), obj.getCompanyName(), obj.getPhone(), obj.getEmail(), companyMapper.convertToDto(obj.getCompany()), obj.getZipCode(), obj.getAddress(), obj.getState(), obj.getType(), obj.isEnabled());
//
//    }
//
//
//}
