package com.vaibhavtraders.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ApiDetails")
public class ApiDetails {
	@Id
    private Long id; // Assuming there's an ID column as primary key
	private String apiHeaderKey;
	private String apiHeaderValue;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getApiHeaderKey() {
		return apiHeaderKey;
	}
	public void setApiHeaderKey(String apiHeaderKey) {
		this.apiHeaderKey = apiHeaderKey;
	}
	public String getApiHeaderValue() {
		return apiHeaderValue;
	}
	public void setApiHeaderValue(String apiHeaderValue) {
		this.apiHeaderValue = apiHeaderValue;
	}
}
