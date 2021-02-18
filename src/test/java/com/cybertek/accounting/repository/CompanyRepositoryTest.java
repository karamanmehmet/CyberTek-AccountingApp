package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Company;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TestEntityManager entityManager;

    /**
     * Authorised user should be able to create company
    */
    @Test
    public void createCompanyTest(){

        Company company = new Company("title","address1","address2","OH","200345","rep","email@email.com", LocalDate.now());
        entityManager.persist(company);

    }


}
