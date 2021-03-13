package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.entity.Category;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.User;
import com.cybertek.accounting.exception.CategoryNotFoundException;
import com.cybertek.accounting.exception.CompanyAlreadyExistsException;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.repository.CompanyRepository;
import com.cybertek.accounting.repository.UserRepository;
import com.cybertek.accounting.service.CompanyService;
import com.cybertek.accounting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final MapperGeneric mapperGeneric;
    private final UserRepository userRepository;


    @Override
    public CompanyDto findById(Long id) throws CompanyNotFoundException {

        Company company = companyRepository.findById(id)
                .orElseThrow(()->new CompanyNotFoundException("This company does not exist"));
        return mapperGeneric.convert(company,new CompanyDto());
    }

    @Override
    public CompanyDto findByEmail(String email) throws CompanyNotFoundException {
        Company foundCompany = companyRepository.findByEmail(email)
                .orElseThrow(() -> new CompanyNotFoundException("This company does not exist!"));
        return mapperGeneric.convert(foundCompany,new CompanyDto());
    }

    @Transactional
    @Override
    public CompanyDto create(CompanyDto companyDto) throws CompanyAlreadyExistsException {

        Optional<Company> foundCompany = companyRepository.findByEmail(companyDto.getEmail());

        if (foundCompany.isPresent()) throw new CompanyAlreadyExistsException("This company already exists");

        companyRepository.saveAndFlush(mapperGeneric.convert(companyDto,new Company()));

        return companyDto;
    }

    @Override
    public List<CompanyDto> findAll() {

        return companyRepository.findAll().stream()
                .map(company -> mapperGeneric.convert(company,new CompanyDto()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CompanyDto update(CompanyDto companyDto) throws CompanyNotFoundException {

        Company foundCompany = companyRepository.findById(companyDto.getId())
                .orElseThrow(() -> new CompanyNotFoundException("This company does not exist"));

        foundCompany.setTitle(companyDto.getTitle());
        foundCompany.setZip(companyDto.getZip());
        foundCompany.setAddress1(companyDto.getAddress1());
        foundCompany.setEmail(companyDto.getEmail());
        foundCompany.setEmail(companyDto.getEmail());
        foundCompany.setState(companyDto.getState());
        foundCompany.setEnabled(companyDto.isEnabled());
        foundCompany.setRepresentative(companyDto.getRepresentative());
        foundCompany.setEstablishmentDate(companyDto.getEstablishmentDate());
        foundCompany.setPhone(companyDto.getPhone());

        companyRepository.saveAndFlush(foundCompany);

        return companyDto;
    }

    @Transactional
    @Override
    public boolean delete(CompanyDto companyDto) throws CompanyNotFoundException {

        Company foundCompany = companyRepository.findById(companyDto.getId())
                .orElseThrow(() -> new CompanyNotFoundException("This company does not exist"));

        foundCompany.setEnabled(false);

        List<User> userList = userRepository.findByCompanyAndEnabled(foundCompany, true);

        for (User user : userList) {
            user.setEnabled(false);
            userRepository.saveAndFlush(user);
        }

//                .forEach(user -> {
//                    user.setEnabled(false);
//                    userRepository.saveAndFlush(user);
//                });

        companyRepository.saveAndFlush(foundCompany);
        return !foundCompany.isEnabled();
    }

    @Override
    public List<CompanyDto> findByState(String state) {

        return companyRepository.findByState(state).stream()
                .map(company -> mapperGeneric.convert(company,new CompanyDto()))
                .collect(Collectors.toList());
    }
}
