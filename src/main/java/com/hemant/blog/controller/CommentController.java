package com.hemant.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hemant.blog.payloads.ApiResponse;
import com.hemant.blog.payloads.CommentDto;
import com.hemant.blog.service.ICommentService;

@RestController
@RequestMapping("/blog-api")
public class CommentController {

	@Autowired
	private ICommentService commentService;

	@PostMapping("/post/{postId}/comment")
	public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId) {

		CommentDto comment = commentService.createComment(commentDto, postId);

		return new ResponseEntity<CommentDto>(comment, HttpStatus.OK);
	}

	@GetMapping("/comment/{commentId}")
	public ResponseEntity<CommentDto> getComment(@PathVariable Integer commentId) {
		CommentDto comment = commentService.getComment(commentId);

		return new ResponseEntity<CommentDto>(comment, HttpStatus.OK);
	}
	
	@DeleteMapping("/comment/delete/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
		commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment has deleted",true), HttpStatus.OK);
	}
	
	@GetMapping("/post/{postId}/comments")
	public ResponseEntity<List<CommentDto>> getAllCommentsByPost(@PathVariable Integer postId){
		
		List<CommentDto> comments = commentService.getCommentsByPost(postId);

		return new ResponseEntity<>(comments, HttpStatus.OK);
	}
}
