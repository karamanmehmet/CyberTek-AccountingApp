package com.cybertek.accounting.restcontroller;

import com.cybertek.accounting.dto.ProductDto;
import com.cybertek.accounting.exception.*;
import com.cybertek.accounting.service.CategoryService;
import com.cybertek.accounting.service.CompanyService;
import com.cybertek.accounting.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductRestController {

    private final ProductService service;
    private final CategoryService categoryService;
    private final CompanyService companyService;


    @GetMapping
    public List<ProductDto> getAllProducts(){
        return  service.findAll();
    }


    @GetMapping("/{id}")
    public ProductDto findProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        return  service.findById(id);
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) throws ProductNotFoundException, ProductFieldNullException {
        return  service.update(productDto);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) throws ProductAlreadyExistException, ProductFieldNullException {
        return  service.create(productDto);
    }

    @DeleteMapping("/id}")
    public void delete(@PathVariable("id") Long id) throws ProductNotFoundException {

        service.delete(service.findById(id));
        System.out.println(id+ " Product is deleted");
    }

    @GetMapping("/category/{id}")
    public List<ProductDto> findProductByCategory(@PathVariable("id") Long id) throws CategoryNotFoundException {

        return service.findByCategory(categoryService.findById(id));
    }

    @GetMapping("/category/enable/{id}")
    public List<ProductDto> findProductByCategoryAndStatus(@PathVariable("id") Long id) throws CategoryNotFoundException {

        return service.findByCategoryAndStatus(categoryService.findById(id),true);
    }


    @GetMapping("/company/{email}")
    public List<ProductDto> findProductByCompany(@PathVariable("email") String email ) throws CompanyNotFoundException {

        return service.findByCompany(companyService.findByEmail(email));
    }

    @GetMapping("/company/enable/{email}")
    public List<ProductDto> findProductByCompanyAndStatus(@PathVariable("email") String email) throws CompanyNotFoundException {

        return service.findByCompanyAndStatus(companyService.findByEmail(email),true);
    }

}
