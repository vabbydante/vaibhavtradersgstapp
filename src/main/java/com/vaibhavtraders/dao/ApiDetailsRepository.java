package com.vaibhavtraders.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vaibhavtraders.entity.ApiDetails;

public interface ApiDetailsRepository extends JpaRepository<ApiDetails, Long> {
	ApiDetails findTopBy();
}
