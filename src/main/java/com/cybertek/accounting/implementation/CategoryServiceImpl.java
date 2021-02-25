package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.ProductDto;
import com.cybertek.accounting.entity.Category;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.exception.CategoryHasProductException;
import com.cybertek.accounting.exception.CategoryNotFoundException;
import com.cybertek.accounting.exception.ExistentCategoryException;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.repository.CategoryRepository;
import com.cybertek.accounting.service.CategoryService;
import com.cybertek.accounting.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final MapperGeneric mapper;
    private final ProductService productService;

    /*TODO  Should we give ID number when we creating category.
       Otherwise there is no unique value to check category exist or Not */

    @Override
    public CategoryDto create(CategoryDto categoryDto) throws ExistentCategoryException {


        Optional<Category> foundedCategory = repository.findById(categoryDto.getId());

        if(foundedCategory.isPresent())
            throw new ExistentCategoryException("Category Already exist");

        return mapper.convert(repository.saveAndFlush(mapper.convert(categoryDto,new Category())),new CategoryDto());

    }

    @Override
    public CategoryDto findById(long id) throws CategoryNotFoundException {

        Category foundedCategory = repository.findById(id)
                .orElseThrow(()->new CategoryNotFoundException("This category does not exist"));
        return mapper.convert(foundedCategory,new CategoryDto());
    }

    @Override
    public List<CategoryDto> findAll() {

        List<Category> list = repository.findAll();

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new CategoryDto()); })
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> findAllByCompany(CompanyDto companyDto) {

        Company convertedCompany = mapper.convert(companyDto, new Company());
        List<Category> list = repository.findAllByCompany(convertedCompany);

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new CategoryDto()); })
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> findAllByCompanyAndStatus(CompanyDto companyDto, boolean enabled) {

        Company convertedCompany = mapper.convert(companyDto, new Company());

        List<Category> list = repository.findAllByCompanyAndEnabled(convertedCompany,enabled);

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new CategoryDto()); })
                .collect(Collectors.toList());
    }


    @Override
    public CategoryDto update(CategoryDto categoryDto) throws CategoryNotFoundException {

        Optional<Category> foundedCategory = repository.findById(categoryDto.getId());

        if (foundedCategory.isEmpty()) {
            throw new CategoryNotFoundException("There is no category");
        }
        return mapper.convert(repository.saveAndFlush(mapper.convert(categoryDto,new Category())),new CategoryDto());
    }

    @Override
    public void delete(CategoryDto categoryDto) throws CategoryNotFoundException, CategoryHasProductException {

        Category foundedCategory = repository.findById(categoryDto.getId())
                .orElseThrow(()->new CategoryNotFoundException("There is no record with this "));

        CategoryDto convertedCategory = mapper.convert(foundedCategory, new CategoryDto());

        List<ProductDto> products = productService.findByCategory(convertedCategory);

        if(products.size()>0)
            throw new CategoryHasProductException("This category has products");


        foundedCategory.setEnabled(false);

        repository.saveAndFlush(foundedCategory);

    }

}
