package com.hemant.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hemant.blog.entity.Comment;
import com.hemant.blog.entity.Post;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

	public List<Comment> findByPost(Post post);
}
