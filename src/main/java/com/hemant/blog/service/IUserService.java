package com.hemant.blog.service;

import java.util.List;

import com.hemant.blog.payloads.UserDto;

public interface IUserService {

	UserDto addUser(UserDto userDto);
	UserDto updateUser(Integer id, UserDto userDto);
	UserDto getUser(Integer id);
	List<UserDto> getUsers();
	void removeUser(Integer id);
}
