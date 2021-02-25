package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {

    List<Company> findByState(String state);

    Optional<Company> findByEmail(String email);

    List<Company> findByEnabled(boolean enabled);

    @Query(value = "SELECT * FROM company c JOIN users u ON c.id = u.company_id WHERE u.id = ?1",nativeQuery = true)
    Optional<Company> findCompanyByUser(Long userId);




}
