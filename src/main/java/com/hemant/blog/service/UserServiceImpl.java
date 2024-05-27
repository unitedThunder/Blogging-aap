package com.hemant.blog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hemant.blog.entity.User;
import com.hemant.blog.exceptions.ResourceNotFoundException;
import com.hemant.blog.payloads.UserDto;
import com.hemant.blog.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public UserDto addUser(UserDto userDto) {
//		User user = new User();
//		
//		BeanUtils.copyProperties(userDto, user);
//		
//		User savedUser = userRepo.save(user);
//		
//		UserDto updatedUser = new UserDto();
//		
//		BeanUtils.copyProperties(savedUser, updatedUser);
		User user = modelMapper.map(userDto, User.class);
		User savedUser = userRepo.save(user);
		return modelMapper.map(savedUser, UserDto.class);
	}

	@Override
	public UserDto updateUser(Integer id, UserDto userDto) {
		
		Optional<User> userById = userRepo.findById(id);
		
		if(userById.isPresent()) {
			User user = new User();
			userDto.setId(id);
			BeanUtils.copyProperties(userDto, user);
			userRepo.save(user);
			UserDto updatedUser = new UserDto();
			BeanUtils.copyProperties(user,updatedUser);
			
			return updatedUser;
		}
		else {
			throw new ResourceNotFoundException("User not found with id : "+id);
		}
		
	}

	@Override
	public List<UserDto> getUsers() {
		List<User> allUsers = userRepo.findAll();
		List<UserDto> userDtos = new ArrayList<>();
		
		for(User u:allUsers) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(u, userDto);
			userDtos.add(userDto);
		}
		
		return userDtos;
	}

	@Override
	public void removeUser(Integer id) {
		
		userRepo.deleteById(id);
		
	}

	@Override
	public UserDto getUser(Integer id) {
		User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found With id :"+id));
		UserDto userDto= new UserDto();
		BeanUtils.copyProperties(user, userDto);
		return userDto;
	}

}
