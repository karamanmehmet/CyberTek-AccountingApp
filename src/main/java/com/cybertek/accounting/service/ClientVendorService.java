package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.ClientVendorDto;
import com.cybertek.accounting.enums.ClientVendorType;
import com.cybertek.accounting.exception.ClientVendorNotFoundException;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.ClientVendorAlreadyExistException;


import java.util.List;

public interface ClientVendorService {

    ClientVendorDto create(ClientVendorDto clientVendor) throws ClientVendorAlreadyExistException, CompanyNotFoundException;

    List<ClientVendorDto> findAll() throws CompanyNotFoundException;

    ClientVendorDto findByEmailAndType(String email, ClientVendorType type) throws CompanyNotFoundException;

    ClientVendorDto findByEmail(String email) throws ClientVendorNotFoundException;

    ClientVendorDto findById(Long id) throws ClientVendorNotFoundException;

    List<ClientVendorDto> findAllByType(ClientVendorType type);

    List<ClientVendorDto> findAllByTypeIsNot(ClientVendorType type);

    List<ClientVendorDto> findAllByStateAndType(String state, ClientVendorType type);

    List<ClientVendorDto> findAllByCompany(CompanyDto company);

    List<ClientVendorDto> findAllByCompanyAndType(CompanyDto company, ClientVendorType type);

    List<ClientVendorDto> findAllByState(String state);

    ClientVendorDto update(ClientVendorDto clientVendor,long id) throws ClientVendorNotFoundException, CompanyNotFoundException, ClientVendorAlreadyExistException;


    void delete(long id) throws ClientVendorNotFoundException;



    void delete(ClientVendorDto clientVendor) throws ClientVendorNotFoundException;
    ClientVendorDto update(ClientVendorDto clientVendor) throws ClientVendorNotFoundException, CompanyNotFoundException;

}

