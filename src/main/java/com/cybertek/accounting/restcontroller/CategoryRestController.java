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

    /**
     * This method will be just testing purpose.
     * -----Will not use UI------
     * @return List<CategoryDto>
     */
    @GetMapping
    public List<CategoryDto> getAllCategories(){
        return  service.findAll();
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id ) throws Exception {

         service.delete(service.findById(id));
         System.out.println(id+ " Category is deleted");
    }

    @PutMapping
    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto) throws Exception {
       return  service.update(categoryDto);
    }

    @PostMapping
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) throws Exception {
        return  service.create(categoryDto);
    }

    @GetMapping("/{id}")
    public CategoryDto findCategory(@PathVariable Long id) throws Exception {
        return  service.findById(id);
    }

    @GetMapping("/company/{email}")
    public List<CategoryDto> findCategoryByCompany(@PathVariable String  email) throws Exception {

        return  service.findAllByCompany(companyService.findByEmail(email));
    }

    @GetMapping("/company/enable/{email}")
    public List<CategoryDto> findCategoryByCompanyAndStatus(@PathVariable String  email) throws Exception {
        return  service.findAllByCompanyAndStatus(companyService.findByEmail(email),true);
    }

}
