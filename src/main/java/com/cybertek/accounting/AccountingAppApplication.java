package com.cybertek.accounting;

import com.cybertek.accounting.entity.*;
import com.cybertek.accounting.entity.InvoiceNumber;
import com.cybertek.accounting.enums.ClientVendorType;
import com.cybertek.accounting.enums.InvoiceStatus;
import com.cybertek.accounting.enums.InvoiceType;
import com.cybertek.accounting.enums.Unit;
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
	public CommandLineRunner mappingDemo(RoleRepository roleRepository, UserRepository userRepository, CompanyRepository companyRepository, CategoryRepository categoryRepository, ProductRepository productRepository, InvoiceNumberRepository invoiceNumberRepository, InvoiceRepository invoiceRepository, InvoiceProductRepository invoiceProductRepository, ClientVendorRepository clientVendorRepository
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
			Company companyRoot = new Company("CyberTek LLC","address1","address2","OH","75074","rep","email@email.com","+19823642342", LocalDate.now(),true);

			companyRepository.save(companyRoot);

			User userRoot = new User("Mehmet","Kara","mehmetkara@gmail.com" , true, "+1954784236",
					"$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK",companyRoot);
			userRoot.addRole(roleRoot);

			userRepository.save(userRoot);



 		//Create Sample Company
			Company crustyCompany = new Company("CrustyCloud LLC","address1","address2","OH","75074","Tolga Savaci","karaman@crustycloud.com","+19823642342", LocalDate.now(),true);


			companyRepository.save(crustyCompany);



			//Create InvoiceNumber for Company

			InvoiceNumber invoiceNumber = new InvoiceNumber(crustyCompany,2020,1);
			invoiceNumber = invoiceNumberRepository.save(invoiceNumber);

			InvoiceNumber invoiceNumber2 = new InvoiceNumber(crustyCompany,2021,1);
			invoiceNumber2 = invoiceNumberRepository.save(invoiceNumber2);


		//Create User for Company - Please continue creating with employee user
			User userAdmin = new User("admin","Kara","admin@crustycloud.com" , true, "+1954784236",
					"$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK",crustyCompany);
			userAdmin.addRole(roleAdmin);
			 userRepository.saveAndFlush(userAdmin);



			User userManager = new User("manager","Kara","manager@crustycloud.com" , true, "+1954784236",
					"$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK",crustyCompany);
			userManager.addRole(roleManager);
			userRepository.saveAndFlush(userManager);



			User userEmployee = new User("employee","Mike","employee@crustycloud.com" , true, "+1954784236",
					"$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK",crustyCompany);
			userEmployee.addRole(roleEmployee);
			userRepository.saveAndFlush(userEmployee);



		//Create Product Category

			Category electronicsCategory = new Category("electronics",crustyCompany,true);

			categoryRepository.saveAndFlush(electronicsCategory);

			Category beautyCategory = new Category("beauty",crustyCompany,true);

			categoryRepository.saveAndFlush(beautyCategory);



		//Create Product

			Product smartPhone = new Product("Smart Phone","Blue smartphone",10,electronicsCategory, Unit.PIECE,
					4,crustyCompany,true);

			productRepository.saveAndFlush(smartPhone);

			Product showerGel = new Product("Sport showerGel","Very nice shower gel",20,beautyCategory, Unit.PIECE,
					6,crustyCompany,true);

			productRepository.saveAndFlush(showerGel);



		//Create SP Table 1- Vendor 1 Client

			ClientVendor vendorSP = new ClientVendor("Active azure","+142356662","active@azure.com",crustyCompany,
					ClientVendorType.VENDOR,"3245324","Auckland Hill 14","TX",true);

			clientVendorRepository.saveAndFlush(vendorSP);

			ClientVendor clientSP = new ClientVendor("Bayou Tracking","+142356662","bayou@tracking.com",crustyCompany,
					ClientVendorType.CLIENT,"234245","Chesterfield Industrial Park 26","PH",true);

			clientVendorRepository.saveAndFlush(clientSP);



		//Create Invoice

			Invoice invoiceSales = new Invoice("INV-001-2020", InvoiceStatus.OPEN, InvoiceType.SALES,LocalDate.now().minusYears(1),
					clientSP,crustyCompany,true);

			invoiceSales=	invoiceRepository.saveAndFlush(invoiceSales);

			Invoice invoicePurchase = new Invoice("INV-001-2021", InvoiceStatus.OPEN, InvoiceType.PURCHASE,LocalDate.now().minusDays(2),
					vendorSP,crustyCompany,true);

		invoiceRepository.saveAndFlush(invoicePurchase);


		//Create Invoice Product

			InvoiceProduct invoiceProduct1 = new InvoiceProduct(2,10,300,smartPhone,invoiceSales, true);

			invoiceProductRepository.save(invoiceProduct1);

			InvoiceProduct invoiceProduct2 = new InvoiceProduct(4,15,3.49,showerGel,invoicePurchase, true);

			invoiceProductRepository.save(invoiceProduct2);


		};
	}



}
