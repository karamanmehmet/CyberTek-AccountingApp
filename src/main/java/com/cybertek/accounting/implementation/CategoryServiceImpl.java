package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.ProductDto;
import com.cybertek.accounting.entity.Category;
import com.cybertek.accounting.entity.Company;
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


    @Override
    public CategoryDto create(CategoryDto categoryDto) throws Exception {


        Optional<Category> foundedCategory = repository.findById(categoryDto.getId());

        if(foundedCategory.isPresent())
            throw new Exception("Category Already exist");

        return mapper.convert(repository.saveAndFlush(mapper.convert(categoryDto,new Category())),new CategoryDto());

    }

    @Override
    public CategoryDto findById(long id) throws Exception {

        Category foundedCategory = repository.findById(id)
                .orElseThrow(()->new Exception("This category does not exist"));
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
    public List<CategoryDto> findByCompany(CompanyDto companyDto) {

        Company convertedCompany = mapper.convert(companyDto, new Company());
        List<Category> list = repository.findAllByCompany(convertedCompany);

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new CategoryDto()); })
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> findByCompanyAndStatus(CompanyDto companyDto, boolean enabled) {

        Company convertedCompany = mapper.convert(companyDto, new Company());

        List<Category> list = repository.findAllByCompanyAndEnabled(convertedCompany,enabled);

        return list.stream()
                .map(obj->
                { return mapper.convert(obj,new CategoryDto()); })
                .collect(Collectors.toList());
    }


    @Override
    public CategoryDto update(CategoryDto categoryDto) throws Exception {

        Optional<Category> foundedCategory = repository.findById(categoryDto.getId());

        if (foundedCategory.isEmpty()) {
            throw new Exception("There is no category");
        }
        return mapper.convert(repository.saveAndFlush(mapper.convert(categoryDto,new Category())),new CategoryDto());
    }

    @Override
    public void delete(CategoryDto categoryDto) throws Exception {

        Category foundedCategory = repository.findById(categoryDto.getId())
                .orElseThrow(()->new Exception("There is no record with this "));

        List<ProductDto> products = productService.findByCategory(foundedCategory);

        if(products.size()>0)
            throw new Exception("This category has products");


        foundedCategory.setEnabled(false);

        repository.saveAndFlush(foundedCategory);

    }
}
