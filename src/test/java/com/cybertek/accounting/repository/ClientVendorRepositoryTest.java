package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.ClientVendor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ClientVendorRepositoryTest {



    @Autowired
    private ClientVendorRepository clientVendorRepository;

    @Autowired
    private TestEntityManager entityManager;

    /**
     * User should create Companies
     */
    @Test
    public void testCreateCompany(){
       // Company company=new Company("Abc","add1","add2","state","06170","representitive","email", LocalDate.now());
     //   Company company2=new Company("Def","add11","add22","state2","06171","representitive2","email", LocalDate.now());
     //   entityManager.persist(company);
      //  entityManager.persist(company2);

    }
    /**
     * User should create ClientVendor with existing Company
     */
    @Test
    public void testCreateClientVendorWithCompany(){
        Company company=entityManager.find(Company.class,1L);
        //   ClientVendor clientVendor=new ClientVendor("NewAbc","55555","newEmail",company,"Type",11111,"address","state");
        //  entityManager.persist(clientVendor);

    }

    /**
     * User should create ClientVendor and Company at same time
     */
    @Test
    public void testCreateNewClientVendorWithNewCompany(){
        // Company company3=new Company("LastOne","add111","add222","state3","06172","representitive3","email", LocalDate.now());
        //  ClientVendor clientVendor=new ClientVendor("NewAbc1","55555","newEmail1",company3,"Type",22222,"address2","state2");
        //  clientVendorRepository.save(clientVendor);

    }

    /**
     * Related ClientVendor Should see
     */
    @Test
    public void testGetClientVendor(){
        ClientVendor clientVendor = clientVendorRepository.findById(1L).get();
        System.out.println(clientVendor.toString());
    }

    /**
     * After delete ClientVendor,Company should not delete
     */
    @Test
    public void testDeleteClientVendorTable(){

        clientVendorRepository.deleteById(4L);
    }
}
