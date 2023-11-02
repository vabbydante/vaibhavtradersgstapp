package com.vaibhavtraders.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaibhavtraders.dao.CountryRepository;
import com.vaibhavtraders.entity.Country;
import com.vaibhavtraders.exception.GeneralException;

@Component
public class CountryBO {
	
	@Autowired
	CountryRepository countryRepository;
	
	public Country insert(Country c) throws GeneralException{
		try {
			if(c.getCountryName().isEmpty()) {
				throw new GeneralException("Country name cannot be blank");
			}
		} catch (GeneralException e) {
			throw new GeneralException("Error while inserting country to DB.");
		}
		return countryRepository.save(c);
	}
	
	public List<Country> findAllCountries() throws GeneralException{
		List<Country> allCountriesList = new ArrayList<Country>();
		try {
			allCountriesList = countryRepository.findAll();
			if(allCountriesList == null) {
				throw new GeneralException("No countries found.");
			}
		} catch (GeneralException e) {
			throw new GeneralException("Error while fetching countries.");
		}
		return allCountriesList;
	}
	
	public Country deleteCountry(Country c) throws GeneralException {
		try {
			if(c == null) {
				throw new GeneralException("Param id Country c is null. Can't delete.");
			}
		} catch (GeneralException e) {
			throw new GeneralException("Error while deleting Specific country.");
		}
		countryRepository.delete(c);
		System.out.println("Country with ID : " + c.getCountryID() + " deleted successfully.");
		return c;
	}
}
