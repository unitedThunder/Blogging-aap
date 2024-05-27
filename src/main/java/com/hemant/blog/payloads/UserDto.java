package com.hemant.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	Integer id;
	@NotEmpty(message = "Name must not be empty")
	String userName;
	@Email(message = "Not Valid Email Id")
	String email;
	@NotEmpty
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",message = "Password must be contain Atleast 8 alfa-numeric chars and symbols")
	String password;
	@NotEmpty(message = "Write something in your about")
	String about;
}
