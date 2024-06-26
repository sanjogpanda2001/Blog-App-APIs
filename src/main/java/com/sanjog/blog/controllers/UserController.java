package com.sanjog.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanjog.blog.payloads.ApiResponse;
import com.sanjog.blog.payloads.UserDTO;
import com.sanjog.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//Post
	@PostMapping("/")
	private ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userdto){
		UserDTO createdUserDTO=this.userService.createUser(userdto);
		return new ResponseEntity<>(createdUserDTO,HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	private ResponseEntity<UserDTO> getUserById(@PathVariable Integer id){
		UserDTO gotUserDTO=this.userService.getUserById(id);
		return new ResponseEntity<>(gotUserDTO,HttpStatus.OK);
		
	}
	
	@GetMapping("/")
	private ResponseEntity<List<UserDTO>> getUsers(){
		List<UserDTO> gotUserDTO=this.userService.getAllUsers();
		return new ResponseEntity<>(gotUserDTO,HttpStatus.OK);
		
	}
	
	@PutMapping("/{id}")
	private ResponseEntity<UserDTO> UpdateUser(@Valid @RequestBody UserDTO userdto,@PathVariable Integer id){
		UserDTO updatedUserDTO=this.userService.updateUser(userdto,id);
		return new ResponseEntity<>(updatedUserDTO,HttpStatus.OK);
		
	}
	
	//only admin
	//@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	private ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id){
		this.userService.deleteUser(id);
		return new ResponseEntity<>(new ApiResponse("deleted",true),HttpStatus.OK);
		
	}
	
	
	
	

}
