package com.vaibhavtraders.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.vaibhavtraders.bo.CountryBO;
import com.vaibhavtraders.dto.CountryDTO;
import com.vaibhavtraders.entity.Country;
import com.vaibhavtraders.entity.State;
import com.vaibhavtraders.exception.GeneralException;
import com.vaibhavtraders.response.CountryData;
import com.vaibhavtraders.response.ResponseObject;
import com.vaibhavtraders.response.StateData;

@Service
public class CountryService {
	
	@Autowired
	CountryBO countryBO;
	
	static Logger logger = Logger.getLogger(CountryService.class);
	
	public ResponseObject createCountry(@RequestBody CountryDTO countryDTO) throws GeneralException {
		logger.info("Create Country method triggered inside Service Layer");
		logger.info("Before updating in the table : " + countryDTO);
		ResponseObject responseObject = new ResponseObject();
		try {
			Country c = new Country();
			c.setCountryName(countryDTO.getCountryName());
			Country c1 = countryBO.insert(c);
			
			if(c1 != null) {
				countryDTO.setCountryID(c1.getCountryID());
				countryDTO.setCountryName(c1.getCountryName());
				responseObject.setCountryDTO(countryDTO);
				responseObject.setSuccessMessage("Country inserted successfully for : " + c.getCountryID());
				System.out.println("Country ID : " + countryDTO.getCountryID());
				System.out.println("Country Name : " + countryDTO.getCountryName());
			} else {
				responseObject.setFailureMessage("Error while creating a country.");
				logger.error("Error while creating country for : " + countryDTO.getCountryID());
			}
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while creating country. Contact Admin.");
			logger.error(e);
		}
		return responseObject;
	}
	
	public ResponseObject findCountryById(Long countryID) throws GeneralException {
		logger.info("Find country by id method triggered.");
		ResponseObject responseObject = new ResponseObject();
		try {
			if (countryID == null) {
				responseObject.setFailureMessage("Country Not Found. for given Id : " + countryID);
				return responseObject;
			}
			Country country = countryBO.getCountryById(countryID);
			CountryDTO countryDTO = new CountryDTO();
			if(country != null) {
				countryDTO.setCountryID(country.getCountryID());
				countryDTO.setCountryName(country.getCountryName());
			}
			responseObject.setCountryDTO(countryDTO);
			responseObject.setSuccessMessage("Country found with given ID : " + countryID);
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while finding the Country : " + e.getMessage());
		}
		return responseObject;
	}
	
	public ResponseObject findAllCountries() throws GeneralException {
		logger.info("Find all countries method triggered");
		ResponseObject responseObject = new ResponseObject();
		try {
			List<Country> countryList = countryBO.findAllCountries();
			List<CountryDTO> countryDTOList = new ArrayList<CountryDTO>();
			if(countryList != null) {
				responseObject.setSuccessMessage("Fetching all countries successful.");
				for(Country country : countryList) {
					CountryDTO countryDTO = new CountryDTO();
					countryDTO.setCountryID(country.getCountryID());
					countryDTO.setCountryName(country.getCountryName());
					countryDTOList.add(countryDTO);
					responseObject.setCountryDTOList(countryDTOList);
					System.out.println(countryDTOList);
				}
			} else {
				responseObject.setFailureMessage("Error while fetching all countries");
			}
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while fetching all countries");
			logger.error(e);
		}
		return responseObject;
	}
	
	public ResponseObject deleteCountry(@RequestBody CountryDTO countryDTO) throws GeneralException {
		logger.info("deleteCountry() method triggered");
		ResponseObject responseObject = new ResponseObject();
		try {
			Country country = new Country();
			country.setCountryID(countryDTO.getCountryID());
			countryBO.deleteCountry(country);
			responseObject.setCountry(country);
			responseObject.setSuccessMessage("Deleted Employee with ID : " + countryDTO.getCountryID() + " successful.");
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while deleting country with ID : " + countryDTO.getCountryID());
		}
		return responseObject;
	}
	
	/**
	 * Method to return State Data for State Dropdowns
	 */
	public List<CountryData> getCountryData() {
		logger.info("getCountryData method triggered.");
		List<CountryData> countryData = new ArrayList<CountryData>();
		List<Country> allCountries = new ArrayList<Country>();
		try {
			allCountries = countryBO.findAllCountries();
			if(allCountries.size() > 0) {
				for (Country country : allCountries) {
					CountryData ctryData = new CountryData();
					ctryData.setId(country.getCountryID());
					ctryData.setName(country.getCountryName());
					countryData.add(ctryData);
				}
			}
		} catch (Exception e) {
			logger.error("Error inside getCountryData method in CountryService", e);
			e.printStackTrace();
		}
		return countryData;
	}
}
