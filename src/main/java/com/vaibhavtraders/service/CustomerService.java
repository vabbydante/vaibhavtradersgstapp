package com.vaibhavtraders.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.vaibhavtraders.bo.CustomerBO;
import com.vaibhavtraders.dto.CustomerDTO;
import com.vaibhavtraders.entity.Customer;
import com.vaibhavtraders.exception.GeneralException;
import com.vaibhavtraders.response.ResponseObject;

@Service
public class CustomerService {
	
	@Autowired
	CustomerBO customerBO;
	
	static Logger logger = Logger.getLogger(CustomerService.class);
	
	public ResponseObject createCustomer(@RequestBody CustomerDTO customerDTO) throws GeneralException{
		logger.info("Create Customer method triggered inside Service Layer.");
		logger.info("Before updating in the table : " + customerDTO);
		ResponseObject responseObject = new ResponseObject();
		try {
			Customer c = new Customer();
			c.setGstin(customerDTO.getGstin());
			c.setCompanyName(customerDTO.getCompanyName());
			c.setContactPerson(customerDTO.getContactPerson());
			c.setContactNo(customerDTO.getContactNo());
			c.setEmail(customerDTO.getEmail());
			c.setPan(customerDTO.getPan());
			c.setAddressLine1(customerDTO.getAddressLine1());
			c.setAddressLine2(customerDTO.getAddressLine2());
			c.setLandmark(customerDTO.getLandmark());
			c.setCountry(customerDTO.getCountry());
			c.setState(customerDTO.getState());
			c.setCity(customerDTO.getCity());
			c.setPincode(customerDTO.getPincode());
			c.setWebsite(customerDTO.getWebsite());
			c.setNote(customerDTO.getNote());
			Customer c1 = customerBO.insert(c);
			
			if(c1 != null) {
				customerDTO.setCustomerId(c1.getCustomerId());
				customerDTO.setGstin(c1.getGstin());
				customerDTO.setCompanyName(c1.getCompanyName());
				customerDTO.setContactPerson(c1.getContactPerson());
				customerDTO.setContactNo(c1.getContactNo());
				customerDTO.setEmail(c1.getEmail());
				customerDTO.setPan(c1.getPan());
				customerDTO.setAddressLine1(c1.getAddressLine1());
				customerDTO.setAddressLine2(c1.getAddressLine2());
				customerDTO.setLandmark(c1.getLandmark());
				customerDTO.setCountry(c1.getCountry());
				customerDTO.setState(c1.getState());
				customerDTO.setCity(c1.getCity());
				customerDTO.setPincode(c1.getPincode());
				customerDTO.setWebsite(c1.getWebsite());
				customerDTO.setNote(c1.getNote());
				responseObject.setCustomerDTO(customerDTO);
				responseObject.setSuccessMessage("Customer made successfully for " + c.getCustomerId());
				//all sysouts. not giving now.
			} else {
				responseObject.setFailureMessage("Error in service layer while performing create for Customer");
				logger.error("Error while creating Customer for : " + customerDTO.getCustomerId());
			}
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error in service layer while performing create customer");
			logger.error(e);
		}
		return responseObject;
	}
	
	public ResponseObject findAllCustomers() throws GeneralException {
		logger.info("Find all customers method triggered from service layer");
		ResponseObject responseObject = new ResponseObject();
		try {
			List<Customer> customerList = customerBO.findAllCustomers();
			List<CustomerDTO> customerDTOList = new ArrayList<CustomerDTO>();
			if(customerList != null) {
				for (Customer customer : customerList) {
					CustomerDTO customerDTO = new CustomerDTO();
					customerDTO.setCustomerId(customer.getCustomerId());
					customerDTO.setGstin(customer.getGstin());
					customerDTO.setCompanyName(customer.getCompanyName());
					customerDTO.setContactPerson(customer.getContactPerson());
					customerDTO.setContactNo(customer.getContactNo());
					customerDTO.setEmail(customer.getEmail());
					customerDTO.setPan(customer.getPan());
					customerDTO.setAddressLine1(customer.getAddressLine1());
					customerDTO.setAddressLine2(customer.getAddressLine2());
					customerDTO.setLandmark(customer.getLandmark());
					customerDTO.setCountry(customer.getCountry());
					customerDTO.setState(customer.getState());
					customerDTO.setCity(customer.getCity());
					customerDTO.setPincode(customer.getPincode());
					customerDTO.setWebsite(customer.getWebsite());
					customerDTO.setNote(customer.getNote());
					customerDTOList.add(customerDTO);
					responseObject.setCustomerDTOList(customerDTOList);
					responseObject.setSuccessMessage("Fetching all Customers successful.");
					System.out.println(customerDTOList);
				}
			} else {
				responseObject.setFailureMessage("Error while fetching all Customers Data.");
			}
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while fetching all Customers Data.");
			logger.error(e);
		}
		return responseObject;
	}
	
	public ResponseObject deleteCustomer(@RequestBody CustomerDTO customerDTO) throws GeneralException {
		logger.info("deleteCustomer() method inside Service layer triggered.");
		ResponseObject responseObject = new ResponseObject();
		try {
			Customer customer = new Customer();
			customer.setCustomerId(customerDTO.getCustomerId());
			customerBO.deleteCustomer(customer);
			responseObject.setCustomer(customer);
			responseObject.setSuccessMessage("Deleted Customer with ID : " + customerDTO.getCustomerId());
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while deleting customer with ID : " + customerDTO.getCustomerId());
		}
		return responseObject;
	}
}
