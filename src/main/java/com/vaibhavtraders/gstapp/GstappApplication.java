package com.vaibhavtraders.gstapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.vaibhavtraders.bo.ProductBO;
import com.vaibhavtraders.entity.Product;
import com.vaibhavtraders.enums.ProductType;
import com.vaibhavtraders.exception.GeneralException;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan("com.vaibhavtraders")
@EntityScan(basePackages = {"com.vaibhavtraders.entity"}) 
@EnableJpaRepositories("com.vaibhavtraders.dao")
public class GstappApplication {

	public static void main(String[] args) throws GeneralException {
		// ARCHITECTURE:
		// Frontend Request > Controller(API Endpoints) > Service > BO > Repository > Backend Repository.
		/*                   INNER METHODS AND DATABASE TRANSACTION                     */
		// Backend Response > Repository > BO > Service > Controller(API Endpoints) > Frontend.
		ApplicationContext ctx = SpringApplication.run(GstappApplication.class, args);
	}
}
