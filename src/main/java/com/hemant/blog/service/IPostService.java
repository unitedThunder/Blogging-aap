package com.hemant.blog.service;

import java.util.List;

import com.hemant.blog.entity.Category;
import com.hemant.blog.entity.User;
import com.hemant.blog.payloads.PostDto;
import com.hemant.blog.payloads.PostResponse;

public interface IPostService {

	PostDto createPost(PostDto post,Integer catId, Integer uid);
	PostDto updatePost(PostDto post, Integer postId);
	PostDto getPost(Integer pid);
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	PostResponse getPostByUser(Integer uid, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	PostResponse getPostByCategory(Integer catId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	List<PostDto> serchByKeword(String keyword);
	void deletePost(Integer postId);
}
