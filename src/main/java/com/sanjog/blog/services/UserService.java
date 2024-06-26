package com.sanjog.blog.services;

import java.util.List;

import com.sanjog.blog.entities.User;
import com.sanjog.blog.payloads.UserDTO;

public interface UserService {

	UserDTO registerNewUser(UserDTO user);
	UserDTO createUser(UserDTO user);
	UserDTO updateUser(UserDTO user, Integer userId);
	UserDTO getUserById(Integer userId);
	List<UserDTO> getAllUsers();
	void deleteUser(Integer userId);
}
