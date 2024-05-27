package com.hemant.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hemant.blog.payloads.ApiResponse;
import com.hemant.blog.payloads.CategoryDto;
import com.hemant.blog.service.ICategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/blog-api/category")
public class CategoryController {

	@Autowired
	private ICategoryService catServ;
	
	@PostMapping("/add")
	public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto catDto){
		CategoryDto category = catServ.addCategory(catDto);
		return new ResponseEntity<CategoryDto>(category,HttpStatus.CREATED);
		
	}
	
	@PutMapping("/update/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto catDto, @PathVariable Integer catId){
		
		CategoryDto category = catServ.updateCategory(catDto, catId);
		return ResponseEntity.ok(category);
	}
	
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId){
		CategoryDto category = catServ.getCategory(catId);
		return new ResponseEntity<CategoryDto>(category,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCategories(){
		List<CategoryDto> categories = catServ.getCategories();
		return new ResponseEntity<List<CategoryDto>>(categories, HttpStatus.OK);
	}
	
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId){
		CategoryDto category = catServ.getCategory(catId);
		catServ.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(category.getCategoryTitle()+" Category Deleted", true), HttpStatus.OK);
	}
}
