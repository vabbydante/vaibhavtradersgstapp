package com.vaibhavtraders.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DeliveryMode {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryModeID;
    
    @Column(nullable = false)
    private String modeName;

	public Long getDeliveryModeID() {
		return deliveryModeID;
	}

	public void setDeliveryModeID(Long deliveryModeID) {
		this.deliveryModeID = deliveryModeID;
	}

	public String getModeName() {
		return modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}
    
}
