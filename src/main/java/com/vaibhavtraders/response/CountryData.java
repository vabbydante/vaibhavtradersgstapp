package com.vaibhavtraders.response;

/**
 * @apiNote This POJO class is used to build State Data to populate State Drop-downs
 * @author Vaibhav Gupta
 */
public class CountryData {
	private Long id;
	private String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
