package com.cybertek.accounting;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.InvoiceNumber;
import com.cybertek.accounting.entity.Role;
import com.cybertek.accounting.entity.User;
import com.cybertek.accounting.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@SpringBootApplication
public class AccountingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountingAppApplication.class, args);
	}

	@Bean
	@Transactional
	public CommandLineRunner mappingDemo(RoleRepository roleRepository, UserRepository userRepository, CompanyRepository companyRepository, CategoryRepository categoryRepository, ProductRepository productRepository, InvoiceNumberRepository invoiceNumberRepository,InvoiceRepository invoiceRepository,InvoiceProductRepository invoiceProductRepository, SPTableRepository spTableRepository
										 )
	{
		return args -> {
		//Create Roles

			Role roleRoot     = new Role("ROLE_ROOT");
			Role roleAdmin    = new Role("ROLE_ADMIN");
			Role roleManager  = new Role("ROLE_MANAGER");
			Role roleEmployee = new Role("ROLE_EMPLOYEE");

			 roleRepository.save(roleRoot);
			roleRepository.save(roleAdmin);
			roleRepository.save(roleManager);
			roleRepository.save(roleEmployee);

		//Create Root Company & Users
			Company companyRoot = new Company("CyberTek LLC","address1","address2","OH","75074","rep","email@email.com", LocalDate.now(),true);

			companyRepository.save(companyRoot);

			User userRoot = new User("Mehmet","Kara","mehmetkara@gmail.com" , true, "+1954784236",
					"password",companyRoot);
			userRoot.addRole(roleRoot);

			userRepository.save(userRoot);

		//Create Sample Company
			Company companySample = new Company("CrustyCloud LLC","address1","address2","OH","75074","Tolga Savaci","karaman@crustycloud.com", LocalDate.now(),true);


			companyRepository.save(companySample);

			Company crustyCompany = companyRepository.findById(2l).get();

			//Create InvoiceNumber for Company

			InvoiceNumber invoiceNumber = new InvoiceNumber(crustyCompany,2021,1);
			invoiceNumber = invoiceNumberRepository.save(invoiceNumber);


		//Create User for Company - Please continue creating with employee user
			User userAdmin = new User("admin","Kara","admin@crustycloud.com" , true, "+1954784236",
					"password",crustyCompany);
			userAdmin.addRole(roleAdmin);
			 userRepository.saveAndFlush(userAdmin);



			User userManager = new User("manager","Kara","manager@crustycloud.com" , true, "+1954784236",
					"password",crustyCompany);
			userManager.addRole(roleManager);
			userRepository.saveAndFlush(userManager);


		//Create Product Category

		//Create Product

		//Create SP Table 1- Vendor 1 Client

		//Create Invoice

		//Create Invoice Product





		};
	}

}
