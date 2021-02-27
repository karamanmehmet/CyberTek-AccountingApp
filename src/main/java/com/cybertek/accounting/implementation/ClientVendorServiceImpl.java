package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.ClientVendorDto;
import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.entity.ClientVendor;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.enums.ClientVendorType;
import com.cybertek.accounting.exception.ClientVendorNotFoundException;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.ClientVendorAlreadyExistException;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.repository.ClientVendorRepository;
import com.cybertek.accounting.service.ClientVendorService;
import com.cybertek.accounting.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientVendorServiceImpl implements ClientVendorService {

    private final ClientVendorRepository repository;
    private final MapperGeneric mapper;
    private final CompanyService companyService;





    @Transactional
    @Override
    public ClientVendorDto create(ClientVendorDto clientVendor) throws ClientVendorAlreadyExistException, CompanyNotFoundException {
        // TODO This part will update according to valid user
        Company convertedCompany = mapper.convert(companyService.findByEmail("karaman@crustycloud.com"), new Company());

        Optional<ClientVendor> foundedClientVendor = repository.findByEmailAndCompany(clientVendor.getEmail(),convertedCompany);

        if(foundedClientVendor.isPresent())
                throw new ClientVendorAlreadyExistException("This Client/Vendor is already exist");


        ClientVendor convertedClientVendor = mapper.convert(clientVendor, new ClientVendor());

        convertedClientVendor.setCompany(convertedCompany);
        //convertedCategory.setEnabled(true);
        return mapper.convert(repository.saveAndFlush(convertedClientVendor),new ClientVendorDto());

    }

    @Override
    public List<ClientVendorDto> findAll() {

        List<ClientVendor> list = repository.findAll();

        return list.stream()
                .map(obj->
                     { return mapper.convert(obj,new ClientVendorDto()); })
                .collect(Collectors.toList());
    }

    @Override
    public ClientVendorDto findByEmail(String email) throws ClientVendorNotFoundException {

        ClientVendor foundedCV = repository.findByEmail(email)
                .orElseThrow(()->new ClientVendorNotFoundException("This CV does not exist"));
        return mapper.convert(foundedCV,new ClientVendorDto());
    }

    @Override
    public List<ClientVendorDto> findAllByCompany(CompanyDto company) {

        Company convertedCompany = mapper.convert(company, new Company());

        List<ClientVendor> list = repository.findAllByCompany(convertedCompany);

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new ClientVendorDto()); })
                .collect(Collectors.toList());      }

    @Override
    public List<ClientVendorDto> findAllByCompanyAndType(CompanyDto company, ClientVendorType type) {

        Company convertedCompany = mapper.convert(company, new Company());

        List<ClientVendor> list = repository.findAllByCompanyAndType(convertedCompany,type);

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new ClientVendorDto()); })
                .collect(Collectors.toList());      }

    @Override
    public List<ClientVendorDto> findAllByCompanyAndState(CompanyDto company, String state) {

        Company convertedCompany = mapper.convert(company, new Company());

        List<ClientVendor> list = repository.findAllByCompanyAndState(convertedCompany,state);

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new ClientVendorDto()); })
                .collect(Collectors.toList());      }

    @Transactional
    @Override
    public ClientVendorDto update(ClientVendorDto clientVendor) throws ClientVendorNotFoundException {

        Optional<ClientVendor> foundedClientVendor = repository.findById(clientVendor.getId());

        if(foundedClientVendor.isEmpty())
            throw new ClientVendorNotFoundException("There is no client/Vendor");

        ClientVendor convertedClientVendor = mapper.convert(clientVendor, new ClientVendor());

        convertedClientVendor.setId(foundedClientVendor.get().getId());

        return mapper.convert(repository.saveAndFlush(mapper.convert(clientVendor,new ClientVendor())),new ClientVendorDto());

            }
    @Transactional
    @Override
    public void delete(ClientVendorDto clientVendor) throws ClientVendorNotFoundException {


        ClientVendor foundedClientVendor = repository.findByEmail(clientVendor.getEmail())
                .orElseThrow(()->new ClientVendorNotFoundException("There is no record with this "));


        foundedClientVendor.setEmail(foundedClientVendor.getEmail()+"-"+foundedClientVendor.getId());

        foundedClientVendor.setEnabled(false);

        repository.saveAndFlush(foundedClientVendor);

          }


    @Override
    public List<ClientVendorDto> findAllByCompanyAndStateAndType(CompanyDto company, String state, ClientVendorType type) {
        Company convertedCompany = mapper.convert(company, new Company());

        List<ClientVendor> list = repository.findAllByCompanyAndStateAndType(convertedCompany,state,type);

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new ClientVendorDto()); })
                .collect(Collectors.toList());      }
}
