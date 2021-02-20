package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CategoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateCategory() {

        /*
        Category category1 = new Category("Electronics", companyRepository.findById(1).get());
        Category category2 = new Category("Clothing", companyRepository.findById(2).get());
        Category category3 = new Category("Food & Drink", companyRepository.findById(3).get());

        entityManager.persist(category1);
        entityManager.persist(category2);
        entityManager.persist(category3);
*/
    }

}
