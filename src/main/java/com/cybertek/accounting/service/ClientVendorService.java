package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.ClientVendorDto;


import java.util.List;

public interface ClientVendorService {

    ClientVendorDto create(ClientVendorDto clientVendor);

    List<ClientVendorDto> findAll();

    List<ClientVendorDto> findAllByCompany(CompanyDto company);

    List<ClientVendorDto> findAllByCompanyAndType(CompanyDto company, String type);

    List<ClientVendorDto> findAllByCompanyAndState(CompanyDto company, String state);

    ClientVendorDto update(ClientVendorDto clientVendor);

    void delete(ClientVendorDto clientVendor);

    List<ClientVendorDto> findAllByCompanyAndStateAndType(CompanyDto company, String state, String type);


}

