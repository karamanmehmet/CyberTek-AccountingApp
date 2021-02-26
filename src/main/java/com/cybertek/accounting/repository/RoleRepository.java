package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    List<Role> findAllByEnabled(boolean enabled);
    List<Role> findAll();
    Role findByName(String name);

}
