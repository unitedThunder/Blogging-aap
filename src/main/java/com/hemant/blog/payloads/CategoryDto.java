package com.hemant.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

	private Integer categoryId;
	
	@NotEmpty(message = "Title should not be empty")
	private String categoryTitle;
	
	@NotEmpty(message = "Must not be Empty")
	@Size(min = 20, message = "write description of minimum 20 chars")
	private String categoryDescription;
}
