package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.RoleDto;
import com.cybertek.accounting.entity.Role;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.repository.RoleRepository;
import com.cybertek.accounting.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final MapperGeneric mapper;



    @Override
    public RoleDto create(RoleDto role) throws Exception {
        if (role.getName() == null) {
            throw new Exception("Something went wrong please try again");
        }



        return mapper.convert(roleRepository.saveAndFlush(mapper.convert(role, new Role())),role);
    }

    @Override
    public List<RoleDto> findAll() {
        List<Role> list = roleRepository.findAll();
        return list.stream().map(obj -> {
            return mapper.convert(obj, new RoleDto());
        }).collect(Collectors.toList());
    }
    @Override
    public RoleDto update(RoleDto dto) {
        //Find current Role Entity
        Role foundrole = roleRepository.findByName(dto.getName());
        //Map update user dto to entity object
       Role convertedRole = mapper.convert(dto,new Role());
        //set id to the converted object
        convertedRole.setId(foundrole.getId());
        //save updated Role to DB
        roleRepository.saveAndFlush(convertedRole);
        // call updated entity from DB and convert it to RoleDto and return the RoleDto
        return mapper.convert(roleRepository.findByName(convertedRole.getName()), new RoleDto());
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
