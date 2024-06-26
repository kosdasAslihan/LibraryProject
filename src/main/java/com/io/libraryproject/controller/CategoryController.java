package com.io.libraryproject.controller;

import com.io.libraryproject.dto.CategoryDTO;
import com.io.libraryproject.dto.request.CategoryRequest;
import com.io.libraryproject.dto.response.LbResponse;
import com.io.libraryproject.dto.response.ResponseMessage;
import com.io.libraryproject.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LbResponse> saveCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        categoryService.saveCategory(categoryRequest);

        LbResponse lbResponse = new LbResponse(ResponseMessage.CATEGORY_SAVED_RESPONSE_MESSAGE,true);
        return ResponseEntity.ok(lbResponse);
    }
    @GetMapping("/visitors/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
    public ResponseEntity<List<CategoryDTO>> getAllCategory(){
        List<CategoryDTO> categoryDTOList = categoryService.getAllCategory();
        return ResponseEntity.ok(categoryDTOList);
    }
    @GetMapping("/visitors/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO categoryDTO = categoryService.getCategoryById(id);

        return ResponseEntity.ok(categoryDTO);
    }
    @GetMapping("/visitors/pages")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
    public ResponseEntity<Page<CategoryDTO>> getAllCategoryByPage(@RequestParam("page") int page,
                                                                  @RequestParam("size") int size,
                                                                  @RequestParam("sort") String prop,
                                                                  @RequestParam(value = "direction", required = false,
                                                                  defaultValue = "DESC")Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(direction,prop));
        Page<CategoryDTO> allCategoryByPage = categoryService.getAllCategoryByPage(pageable);

        return ResponseEntity.ok(allCategoryByPage);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LbResponse> updateCategory (@PathVariable Long id,
                                                      @Valid @RequestBody CategoryRequest categoryRequest) {
        categoryService.updateCategory(id,categoryRequest);
        LbResponse lbResponse = new LbResponse(ResponseMessage.CATEGORY_UPDATED_RESPONSE_MESSAGE, true);
        return ResponseEntity.ok(lbResponse);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LbResponse> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        LbResponse lbResponse = new LbResponse(ResponseMessage.CATEGORY_DELETED_RESPONSE_MESSAGE,true);
        return ResponseEntity.ok(lbResponse);
    }

}
