package com.hemant.blog.service;

import java.util.List;

import com.hemant.blog.payloads.CategoryDto;

public interface ICategoryService {

	CategoryDto addCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(CategoryDto categoryDto, Integer catId);
	CategoryDto getCategory(Integer catId);
	List<CategoryDto> getCategories();
	void deleteCategory(Integer catId);
	
}
