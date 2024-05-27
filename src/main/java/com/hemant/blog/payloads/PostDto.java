package com.hemant.blog.payloads;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.hemant.blog.entity.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

	private Integer postId;
	
	@NotEmpty(message = "Could not be empty")
	private String title;
	
	@NotEmpty(message = "Could not be empty")
	@Size(min = 100, message = "Write atleast 100 chars")
	private String content;
	
	private String imageUrl;
	
	private Date date;
	
	private CategoryDto category;
	
	private UserDto user;
	
	private Set<CommentDto> comments= new HashSet<>();
	
}
