package com.ibrahim.drop_shop.services.category;

import com.ibrahim.drop_shop.exceptions.AlreadyExistsException;
import com.ibrahim.drop_shop.exceptions.NotFoundException;
import com.ibrahim.drop_shop.models.Category;
import com.ibrahim.drop_shop.repositories.CategoryRepository;
import com.ibrahim.drop_shop.services.category.DTO.AddCategoryDto;
import com.ibrahim.drop_shop.services.category.DTO.CategoryResponseDto;
import com.ibrahim.drop_shop.services.category.DTO.UpdateCategoryDto;
import com.ibrahim.drop_shop.utils.ResponseTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;
    private final ResponseTransformer responseTransformer;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ResponseTransformer responseTransformer){
        this.categoryRepository = categoryRepository;
        this.responseTransformer = responseTransformer;
    }


    @Override
    public CategoryResponseDto addCategory(AddCategoryDto categoryDto) {
        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new AlreadyExistsException("Category already exists");
        }
        Category category = Category.builder()
                .name(categoryDto.getName())
                .build();
        category = categoryRepository.save(category);
        return responseTransformer.transformToDto(category, CategoryResponseDto.class);
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) {
        Category category =  categoryRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        return responseTransformer.transformToDto(category, CategoryResponseDto.class);
    }

    @Override
    public CategoryResponseDto getCategoryByName(String name) {
        Category category =  categoryRepository.findByName(name);
        return responseTransformer.transformToDto(category, CategoryResponseDto.class);
    }

    @Override
    public CategoryResponseDto updateCategory(UpdateCategoryDto categoryDto, Long id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
       if(categoryDto.getName() != null) {
            category.setName(categoryDto.getName());
            categoryRepository.save(category);
       }
       return responseTransformer.transformToDto(category, CategoryResponseDto.class);
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
    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categories = categoryRepository
                .findAll();

        return categories
                .stream()
                .map(c -> responseTransformer.transformToDto(c, CategoryResponseDto.class))
                .toList();
    }
}
