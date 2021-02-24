package com.cybertek.accounting.restcontroller;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.service.CategoryService;
import com.cybertek.accounting.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryRestController {

    private final CategoryService service;
    private final CompanyService companyService;


    @GetMapping
    public List<CategoryDto> getAllCategories(){
        return  service.findAll();
    }


    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable("id") Long id ) throws Exception {

         service.delete(service.findById(id));
        System.out.println(id+ " Category is deleted");
    }

    @PutMapping("/update")
    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto) throws Exception {
       return  service.update(categoryDto);
    }

    @PostMapping("/create")
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) throws Exception {
        return  service.create(categoryDto);
    }

    @GetMapping("/{id}")
    public CategoryDto findCategory(@PathVariable("id") Long id) throws Exception {
        return  service.findById(id);
    }




    // TODO it returns null so I can not try after this part
    @GetMapping("/company/{email}")
    public List<CategoryDto> findCategoryByCompany(@PathVariable("email") String  email) throws Exception {

        return  service.findByCompany(companyService.findByEmail(email));
    }

    @GetMapping("/company/enable/{email}")
    public List<CategoryDto> findCategoryByCompanyAndStatus(@PathVariable("email") String  email) throws Exception {
        return  service.findByCompanyAndStatus(companyService.findByEmail(email),true);
    }

}
