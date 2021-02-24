package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.ClientVendorDto;
import com.cybertek.accounting.enums.ClientVendorType;


import java.util.List;

public interface ClientVendorService {

    ClientVendorDto create(ClientVendorDto clientVendor) throws Exception;

    List<ClientVendorDto> findAll();

    ClientVendorDto findByEmail(String email) throws Exception;

    List<ClientVendorDto> findAllByCompany(CompanyDto company);

    List<ClientVendorDto> findAllByCompanyAndType(CompanyDto company, ClientVendorType type);

    List<ClientVendorDto> findAllByCompanyAndState(CompanyDto company, String state);

    ClientVendorDto update(ClientVendorDto clientVendor) throws Exception;

    void delete(ClientVendorDto clientVendor) throws Exception;


    List<ClientVendorDto> findAllByCompanyAndStateAndType(CompanyDto company, String state, ClientVendorType type);


}

