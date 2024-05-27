package com.hemant.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hemant.blog.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
