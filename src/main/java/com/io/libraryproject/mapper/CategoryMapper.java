package com.io.libraryproject.mapper;

import com.io.libraryproject.dto.CategoryDTO;
import com.io.libraryproject.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO categoryToCategoryDTO(Category category);

    List<CategoryDTO> mapCategory(List<Category> getAllCategories);
}
