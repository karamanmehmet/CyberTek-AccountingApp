package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.CompanyDto;

import com.cybertek.accounting.dto.UserDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.User;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.repository.UserRepository;
import com.cybertek.accounting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final MapperGeneric mapper;
    private final UserRepository userRepository;

    @Override
    public UserDto create(UserDto userDto) throws Exception {
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        if (user != null) throw new Exception("This user already exists");

        return mapper.convert(userRepository.saveAndFlush(mapper.convert(userDto,new User())),userDto);


    }

    @Override
    public List<UserDto> findAll() {
        List<User> list = userRepository.findAll();
        return list.stream()
                .map(obj-> { return mapper.convert(obj,new UserDto()); })
                .collect(Collectors.toList());
    }

    @Override
    public UserDto update(UserDto userDto) throws Exception {
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());

        if (user.isEmpty()) {
            throw new Exception("The user is not exist");
        }
        userRepository.saveAndFlush(mapper.convert(userDto,new User()));
        return userDto;
    }

    @Override
    public void delete(UserDto userDto) throws Exception {
       User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new Exception("this email is not exist"));
       user.setEmail(userDto.getEmail());
       user.setEnabled(false);
       userRepository.saveAndFlush(user);




    }

    @Override
    public List<UserDto> findByCompanyAndEnabled(CompanyDto companyDto, Boolean enabled) {
        Company company = mapper.convert(companyDto,new Company());

        List<User> list = userRepository.findByCompanyAndEnabled(company,enabled);

        return list.stream()
                .map(obj ->{ return mapper.convert(obj,new UserDto());})
                .collect(Collectors.toList());
    }

}
