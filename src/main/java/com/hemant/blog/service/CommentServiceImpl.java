package com.hemant.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hemant.blog.entity.Comment;
import com.hemant.blog.entity.Post;
import com.hemant.blog.exceptions.ResourceNotFoundException;
import com.hemant.blog.payloads.CommentDto;
import com.hemant.blog.repository.CommentRepo;
import com.hemant.blog.repository.PostRepo;

@Service
public class CommentServiceImpl implements ICommentService {

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PostRepo postRepo;

	@Override
	public CommentDto createComment(CommentDto comment, Integer postId) {

		Comment comment2 = modelMapper.map(comment, Comment.class);
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post not found with id : " + postId));
		comment2.setPost(post);
		Comment savedComment = commentRepo.save(comment2);
		return modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public CommentDto getComment(Integer commentId) {
		Comment comment = commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment not Found with id : " + commentId));
		return modelMapper.map(comment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {

		Comment comment = commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment not Found with id : " + commentId));

		commentRepo.delete(comment);
	}

	@Override
	public List<CommentDto> getCommentsByPost(Integer postId) {
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post not found with id : " + postId));
		List<Comment> comments = commentRepo.findByPost(post);

		List<CommentDto> commentDto = comments.stream().map((comment) -> modelMapper.map(comment, CommentDto.class))
				.collect(Collectors.toList());
		return commentDto;
	}

}
