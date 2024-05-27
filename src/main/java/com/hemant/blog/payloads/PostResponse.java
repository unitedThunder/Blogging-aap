package com.hemant.blog.payloads;

import java.util.List;

import com.hemant.blog.entity.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {

	private List<PostDto> content;
	private Integer pageNumber;
	private Integer pageSize;
	private Integer totalPage;
	private Long totalElement;
	private Boolean isLast;
}
