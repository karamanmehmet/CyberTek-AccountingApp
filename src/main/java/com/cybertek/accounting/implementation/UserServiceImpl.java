package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.CompanyDto;

import com.cybertek.accounting.dto.UserDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.User;
import com.cybertek.accounting.exception.UserAlreadyExist;
import com.cybertek.accounting.exception.UserNotFound;
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
    public UserDto create(UserDto userDto) throws UserAlreadyExist {
        User user = userRepository.findByEmail(userDto.getEmail());
        if (user == null) throw new UserAlreadyExist("This user already exists");

        return mapper.convert(userRepository.saveAndFlush(mapper.convert(userDto, new User())), userDto);


    }

    @Override
    public List<UserDto> findAll() {
        List<User> list = userRepository.findAll();
        return list.stream()
                .map(obj -> {
                    return mapper.convert(obj, new UserDto());
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserDto update(UserDto userDto) throws UserNotFound {
        User user = userRepository.findByEmail(userDto.getEmail());

        if (user == null) {
            throw new UserNotFound("The user is not exist");
        }

        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setPassword(userDto.getPassword());
        user.setActive(userDto.isActive());
        user.setPhone(userDto.getPhone());
        user.setEnabled(user.isEnabled());
        user.setCompany(user.getCompany());
        //user.setRoles(userDto.getRole());

        User savedUser = userRepository.saveAndFlush(user);

        return mapper.convert(savedUser,new UserDto());
    }

    @Override
    public void delete(UserDto userDto) throws Exception {
        User user = userRepository.findByEmail(userDto.getEmail());

        if (user == null) {
            throw new UserNotFound("The user is not exist");
        }

        user.setEmail(userDto.getEmail());
        user.setEnabled(false);
        userRepository.saveAndFlush(user);
    }

    @Override
    public List<UserDto> findByCompanyAndEnabled(CompanyDto companyDto, Boolean enabled) {
        Company company = mapper.convert(companyDto, new Company());

        List<User> list = userRepository.findByCompanyAndEnabled(company, enabled);

        return list.stream()
                .map(obj -> {
                    return mapper.convert(obj, new UserDto());
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findByEmail(String email) {
        return mapper.convert(userRepository.findByEmail(email), new UserDto());
    }

}
