package com.hemant.blog.service;

import java.util.List;

import com.hemant.blog.entity.Comment;
import com.hemant.blog.payloads.CommentDto;

public interface ICommentService {

	CommentDto createComment(CommentDto comment, Integer postId);
	CommentDto getComment(Integer commentId);
	void deleteComment(Integer commentId);
	List<CommentDto> getCommentsByPost(Integer postId);
}
