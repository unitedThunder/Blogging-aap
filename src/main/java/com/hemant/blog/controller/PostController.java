package com.hemant.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hemant.blog.config.AppConstants;
import com.hemant.blog.payloads.ApiResponse;
import com.hemant.blog.payloads.ImageResponse;
import com.hemant.blog.payloads.PostDto;
import com.hemant.blog.payloads.PostResponse;
import com.hemant.blog.service.IFileService;
import com.hemant.blog.service.IPostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/blog-api")
public class PostController {

	@Autowired
	private IPostService postServ;

	@Autowired
	private IFileService fileServ;

	@Value(value = "${project.image}")
	private String path;

	// create post
	@PostMapping("/category/{catId}/user/{uid}/post")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto post, @PathVariable Integer catId,
			@PathVariable Integer uid) {
		PostDto savedPost = postServ.createPost(post, catId, uid);
		return new ResponseEntity<PostDto>(savedPost, HttpStatus.CREATED);

	}

	// get post by category
	@GetMapping("/categories/{catId}/posts")
	public ResponseEntity<PostResponse> getPostByCategory(@PathVariable Integer catId,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		PostResponse posts = postServ.getPostByCategory(catId, pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}

	// get post by user
	@GetMapping("/users/{uid}/posts")
	public ResponseEntity<PostResponse> getPostByUser(@PathVariable Integer uid,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		PostResponse posts = postServ.getPostByCategory(uid, pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}

	// get post by id
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		PostDto post = postServ.getPost(postId);
		return new ResponseEntity<PostDto>(post, HttpStatus.OK);
	}

	// get all post
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		PostResponse posts = postServ.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}

	// update post
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatedPost = postServ.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}

	// delete post
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		postServ.deletePost(postId);

		return new ResponseEntity<ApiResponse>(new ApiResponse("Post has deleted successfuly", true), HttpStatus.OK);
	}

	// search post by keyword
	@GetMapping("/post/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByKeyword(@PathVariable String keyword) {
		List<PostDto> result = postServ.serchByKeword(keyword);
		return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
	}

	// post image
	
	@PostMapping("/post/upload/image/{postId}")
	public ResponseEntity<ImageResponse> uploadImage(@RequestParam(value = "image") MultipartFile file,
			@PathVariable Integer postId) throws IOException {
		String imageName = fileServ.uploadImage(path, file, postId);
		return new ResponseEntity<ImageResponse>(new ImageResponse(imageName, "Image Uploaded"), HttpStatus.OK);
	}
	
	
	@GetMapping("/post/image/{imageName}")
	public void getResource(@PathVariable String imageName, HttpServletResponse response) throws IOException{
		InputStream resource = fileServ.getResource(path, imageName);
		
		StreamUtils.copy(resource, response.getOutputStream());
		
		
	}
}
