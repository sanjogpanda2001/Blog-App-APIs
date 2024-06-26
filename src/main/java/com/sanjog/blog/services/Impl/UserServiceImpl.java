package com.sanjog.blog.services.Impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sanjog.blog.entities.User;
import com.sanjog.blog.exceptions.ResourceNotFoundException;
import com.sanjog.blog.payloads.UserDTO;
import com.sanjog.blog.repositories.UserRepo;
import com.sanjog.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private PasswordEncoder pe;
	
	@Override
	public UserDTO createUser(UserDTO user) {
		// TODO Auto-generated method stub
		User u=this.DtoToUser(user);
		userRepo.save(u);
		UserDTO userDto=UserToDTO(u);
		
		return userDto;
	}

	@Override
	public UserDTO updateUser(UserDTO userdto, Integer userId) {
		// TODO Auto-generated method stub
		User user=this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		
		user.setName(userdto.getName());
		user.setEmail(userdto.getAbout());
		user.setEmail(userdto.getEmail());
		user.setPassword(userdto.getPassword());
		
		this.userRepo.save(user);
		
		return this.UserToDTO(user);
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		// TODO Auto-generated method stub
		User user=this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		return this.UserToDTO(user); 
	}

	@Override
	public List<UserDTO> getAllUsers() {
		// TODO Auto-generated method stub
		List<User>user=this.userRepo.findAll();
		List<UserDTO>userdto=new ArrayList<>();
		for(User u:user) {
			userdto.add(this.UserToDTO(u));
		}
		return userdto;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		User user=this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);
	}
	
	public User DtoToUser(UserDTO userdto) {
		User u=this.modelmapper.map(userdto,User.class);
		
//		u.setId(userdto.getId());
//		u.setName(userdto.getName());
//		u.setEmail(userdto.getEmail());
//		u.setPassword(userdto.getPassword());
//		u.setAbout(userdto.getAbout());
		return u;
	}
	public UserDTO UserToDTO(User user) {
		UserDTO u=this.modelmapper.map(user, UserDTO.class);
//		u.setId(user.getId());
//		u.setName(user.getName());
//		u.setEmail(user.getEmail());
//		u.setPassword(user.getPassword());
//		u.setAbout(user.getAbout());
		return u;
	}

	@Override
	public UserDTO registerNewUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		User u=new User();
		u.setName(userDTO.getName());
		u.setEmail(userDTO.getEmail());
		u.setAbout(userDTO.getAbout());
		//this.modelmapper.map(userDTO, User.class);
		u.setPassword(this.pe.encode(userDTO.getPassword()));
		User newUser=this.userRepo.save(u);
		return this.modelmapper.map(newUser, UserDTO.class);
	}

}
