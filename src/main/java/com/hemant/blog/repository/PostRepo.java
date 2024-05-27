package com.hemant.blog.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hemant.blog.entity.Post;

public interface PostRepo extends JpaRepository<Post, Integer> {

	public Page<Post> findByCategoryId(Integer id, Pageable pageable);
	public Page<Post> findByUserId(Integer id, Pageable pageable );
	
	public List<Post> findByTitleContaining(String title);
	
//	@Query("SELECT p FROM Post p WHERE p.title LIKE %:key%")
//	public List<Post> searchByTitle(@Param("key") String title);
	
	
}
