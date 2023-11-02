package com.vaibhavtraders.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaibhavtraders.dao.CustomerRepository;
import com.vaibhavtraders.entity.Customer;
import com.vaibhavtraders.exception.GeneralException;

@Component
public class CustomerBO {

	@Autowired
	CustomerRepository customerRepository;

	public Customer insert(Customer c) throws GeneralException {
		try {
			if ((c.getCompanyName() == null) || (c.getState().getStateID() == null)
					|| (c.getCountry().getCountryID()) == null) {
				throw new GeneralException("Required fields cannot be empty.");
			}
		} catch (GeneralException e) {
			throw new GeneralException("Error while inserting customer details into the DB");
		}
		return customerRepository.save(c);
	}
	
	public List<Customer> findAllCustomers() throws GeneralException {
		List<Customer> allCustomersList = new ArrayList<Customer>();
		try {
			allCustomersList = customerRepository.findAll();
			if(allCustomersList == null) {
				throw new GeneralException("No customers found.");
			}
		} catch(GeneralException e) {
			throw new GeneralException("Error while fetching all customers list.");
		}
		return allCustomersList;
	}
	
	public Customer deleteCustomer(Customer c) throws GeneralException {
		try {
			if(c == null) {
				throw new GeneralException("Param id Customer c is null. Can't delete");
			}
		} catch (GeneralException e) {
			throw new GeneralException("Error while deleting Specific customer.");
		}
		customerRepository.delete(c);
		System.out.println("Customer with ID : " + c.getCustomerId() + " deleted successfully.");
		return c;
	}
}
