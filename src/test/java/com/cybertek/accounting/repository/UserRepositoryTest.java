package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Role;
import com.cybertek.accounting.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.HashSet;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Disabled
    public void testCreateRoles(){

        Role roleRoot = new Role("Root");
        Role roleAdmin = new Role("Admin");
        Role roleManager = new Role("Manager");
        Role roleEmployee = new Role("Employee");

        entityManager.persist(roleAdmin);
        entityManager.persist(roleRoot);
        entityManager.persist(roleManager);
        entityManager.persist(roleEmployee);
    }

    @Test
    public void testCreateUserWithOneRole(){
        Role role = entityManager.find(Role.class,1L);

        User user = new User("Mehmet","Kara","mehmetkara@gmail.com" , true, "+1954784236",
               "password",null);
        user.addRole(role);

        entityManager.persist(user);
    }

    @Test
    public void testCreateUserWithTwoRoles(){

        Role roleAdmin = entityManager.find(Role.class,2L);
        Role roleEmployee = entityManager.find(Role.class,4L);

        User user = new User("Fatma","White","fatma@gmail.com" , true, "+1954784236",
                "password",null);

        user.addRole(roleAdmin);
        user.addRole(roleEmployee);
        entityManager.persist(user);
    }

    @Test
    public void testAssigningRoleToExistingUser(){

        User user = userRepository.findById(2L).get();
        Role roleManager= entityManager.find(Role.class,3L);

        user.addRole(roleManager);


        //implement with ream
    }

    @Test
    public void testRemoveRoleFromExistingUser(){
        User user =userRepository.findById(2L).get();
        Role roleEmployee = entityManager.find(Role.class,4L);

        user.removeRole(roleEmployee);
        //implement with ream
    }

    //explanation for cascade - fetch (without cascade type persist)
    @Test
    public void testCreateNewUserWithNewRole(){

        Role jokerRole = new Role("JokerRole");
        User user = new User("John","Doe","johndoe@gmail.com" , true, "+1954784236",
                "password",null);
        user.addRole(jokerRole);

        userRepository.save(user);
        //implement with ream
    }

    @Test
    public void testGetUser(){
        User user = userRepository.findById(2L).get();
        System.out.println(user.getEmail());
        user.getRoles().stream().forEach(s -> System.out.println(s.getName()));
    }

    @Test
    public void testDeleteUser(){
        userRepository.deleteById(4l);
    }

}
