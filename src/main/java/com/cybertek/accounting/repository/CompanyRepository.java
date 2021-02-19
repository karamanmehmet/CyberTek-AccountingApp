package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {

    List<Company> findByEnabled(boolean enabled);

    @Query(value = "SELECT * FROM companies c JOIN users u ON c.id = u.company_id WHERE c.id = ?1",nativeQuery = true)
    Company findCompanyByUser(Long userId);




}
