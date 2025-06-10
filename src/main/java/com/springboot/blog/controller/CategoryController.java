package com.springboot.blog.controller;

import com.springboot.blog.entity.Category;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    //build Category restAPI
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
     public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto savedCategory = categoryService.addCatgory(categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
     }

     //Build get category rest API
    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") long categoryId){
        CategoryDto categoryDto = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(categoryDto);
    }

    //build GET All Categories API
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // Build update category Rest APi
@PreAuthorize("hasRole('ADMIN')")
@PutMapping("{id}")
    public  ResponseEntity<CategoryDto> updateCategory( @RequestBody CategoryDto categoryDto, @PathVariable("id") Long categoryId){
        return  ResponseEntity.ok(categoryService.updateCategory(categoryDto, categoryId));
    }

    //build delete Category REST API
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory (@PathVariable("id") Long categoryId){
        categoryService.deletCatgory(categoryId);
        return  ResponseEntity.ok("Category deleted successfully.!");
    }
}
