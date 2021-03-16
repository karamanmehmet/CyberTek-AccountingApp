package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.ClientVendorDto;
import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.ProductDto;
import com.cybertek.accounting.dto.UserDto;
import com.cybertek.accounting.entity.ClientVendor;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.Product;
import com.cybertek.accounting.entity.User;
import com.cybertek.accounting.enums.ClientVendorType;
import com.cybertek.accounting.exception.ClientVendorNotFoundException;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.ClientVendorAlreadyExistException;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.repository.ClientVendorRepository;
import com.cybertek.accounting.repository.CompanyRepository;
import com.cybertek.accounting.repository.UserRepository;
import com.cybertek.accounting.service.ClientVendorService;
import com.cybertek.accounting.service.CompanyService;
import com.cybertek.accounting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientVendorServiceImpl implements ClientVendorService {

    private final ClientVendorRepository repository;
    private final MapperGeneric mapper;
    private final CompanyRepository companyRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public ClientVendorDto create(ClientVendorDto clientVendor) throws ClientVendorAlreadyExistException, CompanyNotFoundException {

        Company company = getCompanyFromSecurity();


        Optional<ClientVendor> foundedClientVendor = repository.findByEmailAndCompanyAndEnabled(clientVendor.getEmail(),company,true);
        //Enabled

        if(foundedClientVendor.isPresent())
            throw new ClientVendorAlreadyExistException("This Client or Vendor is Already Exist.Please Try to Update!");


        ClientVendor convertedClientVendor = mapper.convert(clientVendor, new ClientVendor());
        convertedClientVendor.setEnabled(true);

        convertedClientVendor.setCompany(company);

        return mapper.convert(repository.saveAndFlush(convertedClientVendor),new ClientVendorDto());

    }

    @Transactional
    @Override
    public ClientVendorDto update(ClientVendorDto clientVendor,long id) throws CompanyNotFoundException, ClientVendorNotFoundException, ClientVendorAlreadyExistException {

        Company company = getCompanyFromSecurity();

        Optional<ClientVendor> foundedClientVendor = repository.findById(clientVendor.getId());

        if(foundedClientVendor.isEmpty()){
            throw new ClientVendorNotFoundException("There is no client/Vendor");}


        ClientVendor convertedClientVendor = mapper.convert(clientVendor, new ClientVendor());

        convertedClientVendor.setId(foundedClientVendor.get().getId());
        convertedClientVendor.setCompany(company);
        convertedClientVendor.setEnabled(true);

        return mapper.convert(repository.saveAndFlush(mapper.convert(convertedClientVendor,new ClientVendor())),new ClientVendorDto());
    }



    @Override
    public List<ClientVendorDto> findAll() throws CompanyNotFoundException {

        Company company = getCompanyFromSecurity();

        List<ClientVendor> list = repository.findAllByCompany(company);

        return list.stream().sorted(Comparator.comparing(obj->!obj.isEnabled(),Boolean::compareTo))
                .map(obj->
                     { return mapper.convert(obj,new ClientVendorDto()); })
                .collect(Collectors.toList());
    }



    @Override
    public ClientVendorDto findByEmailAndType(String email, ClientVendorType type) throws CompanyNotFoundException {

        Company company = getCompanyFromSecurity();

        return mapper.convert(repository.findByCompanyAndEmailAndType(company,email,type),new ClientVendorDto());


    }

    @Override
    public ClientVendorDto findByEmail(String email) throws ClientVendorNotFoundException {

        ClientVendor foundedCV = repository.findByEmail(email)
                .orElseThrow(()->new ClientVendorNotFoundException("This CV does not exist"));
        return mapper.convert(foundedCV,new ClientVendorDto());
    }

    @Override
    public ClientVendorDto findById(Long id) throws ClientVendorNotFoundException {
        ClientVendor foundedCV = repository.findById(id)
                .orElseThrow(()->new ClientVendorNotFoundException("This CV does not exist"));
        return mapper.convert(foundedCV,new ClientVendorDto());    }

    @Override
    public List<ClientVendorDto> findAllByType(ClientVendorType type) {

        Company company = getCompanyFromSecurity();

        List<ClientVendor> list = repository.findAllByCompanyAndType(company, type);

        return list.stream().map(clientVendor -> {return mapper.convert(clientVendor, new ClientVendorDto());}).collect(Collectors.toList());
    }

    @Override
    public List<ClientVendorDto> findAllByTypeIsNot(ClientVendorType type) {
        Company company = getCompanyFromSecurity();

        List<ClientVendor> list = repository.findAllByCompanyAndTypeIsNot(company, type);

        return list.stream().map(clientVendor -> {return mapper.convert(clientVendor, new ClientVendorDto());}).collect(Collectors.toList());
    }

    @Override
    public List<ClientVendorDto> findAllByCompany(CompanyDto company) {

        //Company company = getCompanyFromSecurity();

        Company convertedCompany = mapper.convert(company, new Company());

        List<ClientVendor> list = repository.findAllByCompany(convertedCompany);

        return list.stream().sorted(Comparator.comparing(obj->!obj.isEnabled(),Boolean::compareTo))
                .map(obj->
                { return mapper.convert(obj,new ClientVendorDto()); })
                .collect(Collectors.toList());
    }


    // No need
    @Override
    public List<ClientVendorDto> findAllByCompanyAndType(CompanyDto company, ClientVendorType type) {

        Company convertedCompany = mapper.convert(company, new Company());

        List<ClientVendor> list = repository.findAllByCompanyAndType(convertedCompany,type);

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new ClientVendorDto()); })
                .collect(Collectors.toList());      }

    @Override
    public List<ClientVendorDto> findAllByState( String state) {

        Company company = getCompanyFromSecurity();

        List<ClientVendor> list = repository.findAllByCompanyAndState(company,state);

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new ClientVendorDto()); })
                .collect(Collectors.toList());      }



    @Transactional
    @Override
    public void delete(long id ) throws ClientVendorNotFoundException {


        ClientVendor foundedClientVendor = repository.findById(id)
                .orElseThrow(()->new ClientVendorNotFoundException("There is no record with this "));


        foundedClientVendor.setEmail(foundedClientVendor.getEmail()+"-"+foundedClientVendor.getId());

        foundedClientVendor.setEnabled(false);

        repository.saveAndFlush(foundedClientVendor);

          }

    @Override
    public void delete(ClientVendorDto clientVendor) throws ClientVendorNotFoundException {

    }

    @Override
    public ClientVendorDto update(ClientVendorDto clientVendor) throws ClientVendorNotFoundException, CompanyNotFoundException {
        return null;
    }

    @Override
    public List<ClientVendorDto> findAllByStateAndType(String state, ClientVendorType type) {

        Company company = getCompanyFromSecurity();

        List<ClientVendor> list = repository.findAllByCompanyAndStateAndType(company,state,type);

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new ClientVendorDto()); })
                .collect(Collectors.toList());      }


   private Company getCompanyFromSecurity() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email);
        return user.getCompany();

    }



}
