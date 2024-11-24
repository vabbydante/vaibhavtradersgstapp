package com.vaibhavtraders.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vaibhavtraders.entity.Auth;

@Repository
public interface UserDetailsRepository extends JpaRepository<Auth, Long> {
	Auth findByUsername(String username);
	
	boolean existsByUsername(String username);  // Method to check if username exists
}
