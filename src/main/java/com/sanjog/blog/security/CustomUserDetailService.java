package com.sanjog.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sanjog.blog.entities.User;
import com.sanjog.blog.exceptions.ResourceNotFoundException;
import com.sanjog.blog.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		// TODO Auto-generated method stub
		User user=this.userRepo.findByEmail(username)
				.orElseThrow(()-> new ResourceNotFoundException("user","email"+username,0));
		return user;
	}

}
