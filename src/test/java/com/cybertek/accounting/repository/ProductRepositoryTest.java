package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Product;
import com.cybertek.accounting.enums.Unit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateProduct() {

        Product product1 = new Product("IphoneX", "Some IphoneX", 5, 30.0, categoryRepository.findById(1L).get(), Unit.PIECE, 15, 30, companyRepository.findById(1).get());
        Product product2 = new Product("T-Shirt", "Some T-Shirts", 15, 18.0, categoryRepository.findById(2L).get(), Unit.PIECE, 25, 18, companyRepository.findById(1).get());
        Product product3 = new Product("Apples", "Some Apples", 25, 18.0, categoryRepository.findById(3L).get(), Unit.PIECE, 50, 18, companyRepository.findById(1).get());

        entityManager.persist(product1);
        entityManager.persist(product2);
        entityManager.persist(product3);

    }

}
