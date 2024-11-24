package com.vaibhavtraders.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaibhavtraders.dao.UserDetailsRepository;
import com.vaibhavtraders.entity.Auth;

@Service
public class UserService {
	@Autowired
	private UserDetailsRepository userDetailsRepository; // Repository for accessing DB
	/*@Autowired
	private PasswordEncoder passwordEncoder;*/

	public Auth saveUser(Auth user) {
		//user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userDetailsRepository.save(user);
	}
	// You can also add other service methods for user logic (e.g., find, delete,
	// etc.)
}
