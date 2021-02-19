package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Category;
import com.cybertek.accounting.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByCompany(Company company);

    List<Category> findAllByCompanyAndEnabled(Company company, boolean enabled);

}
