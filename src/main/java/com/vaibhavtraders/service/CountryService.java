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
import com.vaibhavtraders.exception.GeneralException;
import com.vaibhavtraders.response.ResponseObject;

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
}
