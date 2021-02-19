package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Category;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCompanyAndCategory(Company company, Category category);

    List<Product> findAllByCompanyAndCategoryAndEnabled(Company company, Category category, boolean enabled);

    List<Product> findAllByCompany(Company company);

    List<Product> findAllByCompanyAndEnabled(Company company, boolean enabled);

//    void deleteByCategory(Category category);

}

//TODO Query annotation
