package com.hemant.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hemant.blog.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
