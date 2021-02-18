package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {

    List<Company> findByEnabled(boolean enabled);


}
