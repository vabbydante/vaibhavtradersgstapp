package com.vaibhavtraders.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vaibhavtraders.entity.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long>{

}
