package com.hemant.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hemant.blog.payloads.ApiResponse;
import com.hemant.blog.payloads.UserDto;
import com.hemant.blog.service.IUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/blog-api/Users")
public class UserController {

	@Autowired
	private IUserService userServ;
	
	@PostMapping("/create")
	public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto){
		UserDto user = userServ.addUser(userDto);
		return new ResponseEntity<>(user,HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{userId}")
	public ResponseEntity<UserDto> updateUser(@PathVariable("userId") Integer id, @Valid @RequestBody UserDto userDto){
		UserDto updateUser = userServ.updateUser(id, userDto);
		
		return new ResponseEntity<UserDto>(updateUser, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> allUsers = userServ.getUsers();
		
		return new ResponseEntity<List<UserDto>> (allUsers, HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable("userId") Integer id){
		UserDto user = userServ.getUser(id);
		return new ResponseEntity<UserDto>(user, HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer id){
		userServ.removeUser(id);
		
		return new ResponseEntity<ApiResponse> (new ApiResponse("User deleted",true), HttpStatus.OK);
	}
}
