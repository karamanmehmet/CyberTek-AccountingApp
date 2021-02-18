package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.SPTable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.time.Instant;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class SPTableRepositoryTest {

    @Autowired
    private SPTableRepository spTableRepository;

    @Autowired
    private TestEntityManager entityManager;

    /**
     * User should create Companies
     */
    @Test
    public void testCreateCompany(){
        Company company=new Company("Abc","add1","add2","state","06170","representitive","email", LocalDate.now());
        Company company2=new Company("Def","add11","add22","state2","06171","representitive2","email", LocalDate.now());
        entityManager.persist(company);
        entityManager.persist(company2);

    }
    /**
     * User should create SPTable with existing Company
     */
    @Test
    public void testCreateSPTableWithCompany(){
        Company company=entityManager.find(Company.class,1L);
        SPTable spTable=new SPTable("NewAbc","55555","newEmail",company,"Type",11111,"address","state");
        entityManager.persist(spTable);

    }

    /**
     * User should create SPTable and Company at same time
     */
    @Test
    public void testCreateNewSPWithNewCompany(){
        Company company3=new Company("LastOne","add111","add222","state3","06172","representitive3","email", LocalDate.now());
        SPTable spTable=new SPTable("NewAbc1","55555","newEmail1",company3,"Type",22222,"address2","state2");
        spTableRepository.save(spTable);

    }

    /**
     * Related SPTable Should see
     */
    @Test
    public void testGetSP(){
        SPTable spTable = spTableRepository.findById(1L).get();
        System.out.println(spTable.toString());
    }

    /**
     * After delete SP,Company should not delete
     */
    @Test
    public void testDeleteSPTable(){

        spTableRepository.deleteById(4L);
    }
}
