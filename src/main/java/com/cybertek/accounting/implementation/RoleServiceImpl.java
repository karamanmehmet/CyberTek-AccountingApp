package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.RoleDto;
import com.cybertek.accounting.entity.Product;
import com.cybertek.accounting.entity.Role;
import com.cybertek.accounting.entity.User;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.repository.RoleRepository;
import com.cybertek.accounting.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
   private  RoleRepository roleRepository;
    private MapperGeneric mapper;

    public RoleServiceImpl(RoleRepository roleRepository, MapperGeneric roleMapper) {
        this.roleRepository = roleRepository;
        this.mapper = roleMapper;
    }
    @Override
    public RoleDto create(RoleDto role) throws Exception {
        if(role.getName()==null ) {
            throw new Exception("Something went wrong please try again");
        }

        roleRepository.saveAndFlush(mapper.convert(role,new Role()));

        return role;
    }

    @Override
    public List<RoleDto> findAll() {
        List<Role> list = roleRepository.findAll();
        return list.stream().map(obj -> {return mapper.convert(obj, new Role());}).collect(Collectors.toList());
    }

    @Override
    public RoleDto update(RoleDto dto) {
        Optional<Role> role = Optional.ofNullable(roleRepository.findByName(dto.getName()));

        roleRepository.saveAndFlush(mapper.convert(dto,new Role()));

        return dto;
    }

    @Override
    public void delete(RoleDto roleDto) {
        Role role = roleRepository.findByName(roleDto.getName());
        role.setName(roleDto.getName());
        role.setEnabled(false);
        roleRepository.saveAndFlush(role);
    }


    @Override
    public RoleDto findByName(String name) {
        Role role = roleRepository.findByName(name);
        return mapper.convert(role,new RoleDto());
    }
}
