package com.hemant.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hemant.blog.entity.Category;
import com.hemant.blog.exceptions.ResourceNotFoundException;
import com.hemant.blog.payloads.CategoryDto;
import com.hemant.blog.repository.CategoryRepo;

@Service
public class CategoryImpl implements ICategoryService{

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		Category savedCat = categoryRepo.save(category);
		return this.modelMapper.map(savedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer catId) {
		Category category = categoryRepo.findById(catId).orElseThrow(()-> new ResourceNotFoundException("Category not found with id :"+catId ));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category savedCat = categoryRepo.save(category);
		
		return this.modelMapper.map(savedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto getCategory(Integer catId) {
		Category category = categoryRepo.findById(catId).orElseThrow(()-> new ResourceNotFoundException("Category not found with id :"+catId ));
		
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categories = categoryRepo.findAll();
		List<CategoryDto> allCat = categories.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return allCat;
	}

	@Override
	public void deleteCategory(Integer catId) {
		categoryRepo.deleteById(catId);
		
	}

}
