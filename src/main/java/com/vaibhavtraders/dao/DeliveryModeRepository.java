package com.vaibhavtraders.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vaibhavtraders.entity.DeliveryMode;

@Repository
public interface DeliveryModeRepository extends JpaRepository<DeliveryMode, Long>{

}
