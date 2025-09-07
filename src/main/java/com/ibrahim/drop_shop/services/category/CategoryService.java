package com.ibrahim.drop_shop.services.category;

import com.ibrahim.drop_shop.models.Category;
import com.ibrahim.drop_shop.repositories.CategoryRepository;
import com.ibrahim.drop_shop.services.category.DTO.AddCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Category addCategory(AddCategoryDto categoryDto) {
        return null;
    }

    @Override
    public Category getCategoryById(Long id) {

    }

    @Override
    public Category getCategoryByName(String name) {
        return null;
    }

    @Override
    public Category updateCategory(AddCategoryDto categoryDto, Long id) {
        return null;
    }

    @Override
    public void deleteCategoryById(Long id) {

    }

    @Override
    public List<Category> getAllCategories() {
        return List.of();
    }
}
