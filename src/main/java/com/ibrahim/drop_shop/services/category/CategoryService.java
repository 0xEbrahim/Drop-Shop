package com.ibrahim.drop_shop.services.category;

import com.ibrahim.drop_shop.exceptions.AlreadyExistsException;
import com.ibrahim.drop_shop.exceptions.NotFoundException;
import com.ibrahim.drop_shop.models.Category;
import com.ibrahim.drop_shop.repositories.CategoryRepository;
import com.ibrahim.drop_shop.services.category.DTO.AddCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Optional<Category> addCategory(AddCategoryDto categoryDto) {
        return Optional.ofNullable(Optional
                .of(categoryDto)
                .filter(c -> !categoryRepository.existsByName(categoryDto.getName()))
                .map(category -> {
                    Category newCategory = Category.builder().name(category.getName()).build();
                    return categoryRepository.save(newCategory);
                }).orElseThrow(() -> new AlreadyExistsException("Category already exists")));
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category updateCategory(AddCategoryDto categoryDto, Long id) {
        return Optional.ofNullable(getCategoryById(id))
            .map(category -> {
            category.setName(categoryDto.getName());
            return categoryRepository.save(category);
        }).orElseThrow(() -> new NotFoundException("Category not found"));
    }

    @Override
    public void deleteCategoryById(Long id) {
         categoryRepository
                 .findById(id)
                 .ifPresentOrElse(categoryRepository :: delete, () -> {
                     throw new NotFoundException("Category not found");
                 });
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
