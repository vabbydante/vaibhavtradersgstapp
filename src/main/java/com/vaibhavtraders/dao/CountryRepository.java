package com.vaibhavtraders.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vaibhavtraders.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long>{

}
