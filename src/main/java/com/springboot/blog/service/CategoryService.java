package com.springboot.blog.service;

import com.springboot.blog.payload.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto  addCatgory(CategoryDto categoryDto);

    CategoryDto getCategory( Long categoryId);

    List<CategoryDto> getAllCategories();

    CategoryDto updateCategory (CategoryDto categoryDto, Long categoryId);

    void  deletCatgory (Long categoryId);
}
