package com.vaibhavtraders.dto;

public class CountryDTO {
	private Long countryID;
	private String countryName;
	public Long getCountryID() {
		return countryID;
	}
	public void setCountryID(Long countryID) {
		this.countryID = countryID;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	@Override
	public String toString() {
		return "CountryDTO [countryID=" + countryID + ", countryName=" + countryName + "]";
	}
	
}
