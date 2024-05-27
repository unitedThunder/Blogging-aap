package com.hemant.blog.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hemant.blog.entity.Category;
import com.hemant.blog.entity.Post;
import com.hemant.blog.entity.User;
import com.hemant.blog.exceptions.ResourceNotFoundException;
import com.hemant.blog.payloads.CategoryDto;
import com.hemant.blog.payloads.PostDto;
import com.hemant.blog.payloads.PostResponse;
import com.hemant.blog.payloads.UserDto;
import com.hemant.blog.repository.CategoryRepo;
import com.hemant.blog.repository.PostRepo;
import com.hemant.blog.repository.UserRepository;

@Service
public class PostServiceImpl implements IPostService {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CategoryRepo catRepo;
	@Autowired
	private UserRepository userRepo;

	@Override
	public PostDto createPost(PostDto post, Integer catId, Integer uid) {
		Category category = catRepo.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + catId));
		post.setCategory(modelMapper.map(category, CategoryDto.class));

		User user = userRepo.findById(uid)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + uid));
		post.setUser(modelMapper.map(user, UserDto.class));

		post.setDate(new Date());

		post.setImageUrl("Default.jpg");

		Post newPost = postRepo.save(modelMapper.map(post, Post.class));
		return modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("User not Found with Id : " + postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageUrl(postDto.getImageUrl());
		Post savedPost = postRepo.save(post);
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	// retrieving post by id
	@Override
	public PostDto getPost(Integer pid) {
		Post post = postRepo.findById(pid)
				.orElseThrow(() -> new ResourceNotFoundException("Post not found with id : " + pid));

		return modelMapper.map(post, PostDto.class);
	}

	// retrieving all posts and doing pagination
	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy) : Sort.by(sortBy).descending();
		PageRequest page = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = postRepo.findAll(page);
		List<Post> pageContent = pagePost.getContent();
		List<PostDto> postList = pageContent.stream().map((post) -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postList);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElement(pagePost.getTotalElements());
		postResponse.setTotalPage(pagePost.getTotalPages());
		postResponse.setIsLast(pagePost.isLast());
		return postResponse;
	}

	// retrieving posts by user and doing pagination
	@Override
	public PostResponse getPostByUser(Integer uid, Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir) {
		User user = userRepo.findById(uid)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + uid));
		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy) : Sort.by(sortBy).descending();
		PageRequest p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> posts = postRepo.findByUserId(user.getId(), p);
		List<Post> content = posts.getContent();
		List<PostDto> postDtos = content.stream().map((post) -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElement(posts.getTotalElements());
		postResponse.setTotalPage(posts.getTotalPages());
		postResponse.setIsLast(posts.isLast());
		return postResponse;
	}

	// retrieving posts by category and doing pagination
	@Override
	public PostResponse getPostByCategory(Integer catId, Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir) {
		Category category = catRepo.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + catId));
		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy) : Sort.by(sortBy).descending();
		PageRequest page = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePosts = postRepo.findByCategoryId(category.getId(), page);
		List<Post> pageContent = pagePosts.getContent();
		List<PostDto> postDtos = pageContent.stream().map((post) -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElement(pagePosts.getTotalElements());
		postResponse.setTotalPage(pagePosts.getTotalPages());
		postResponse.setIsLast(pagePosts.isLast());
		return postResponse;
	}

	//Search post by keyword
	@Override
	public List<PostDto> serchByKeword(String keyword) {
		List<Post> result = postRepo.findByTitleContaining(keyword);
		List<PostDto> collectedPost = result.stream().map((res)-> modelMapper.map(res, PostDto.class)).collect(Collectors.toList());
		return collectedPost;
	}

	// deleting posts
	@Override
	public void deletePost(Integer postId) {
		postRepo.deleteById(postId);

	}

}
