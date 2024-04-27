package com.io.libraryproject.service;

import com.io.libraryproject.dto.CategoryDTO;
import com.io.libraryproject.dto.request.CategoryRequest;
import com.io.libraryproject.entity.Category;
import com.io.libraryproject.exception.ResourceNotFoundException;
import com.io.libraryproject.exception.message.ErrorMessage;
import com.io.libraryproject.mapper.CategoryMapper;
import com.io.libraryproject.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public void saveCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setSequence(categoryRequest.getSequence());

        categoryRepository.save(category);
    }
    public List<CategoryDTO> getAllCategory() {
        List<Category> getAllCategories = categoryRepository.findAll();
        return categoryMapper.mapCategory(getAllCategories);
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(ErrorMessage.CATEGORY_NOT_FOUND_EXCEPTION));
        return categoryMapper.categoryToCategoryDTO(category);
    }
    public Page<CategoryDTO> getAllCategoryByPage(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);

        return categories.map(categoryMapper::categoryToCategoryDTO);
    }
}
