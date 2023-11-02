package com.vaibhavtraders.service;

/**
 * @author Vaibhav Gupta
 *
 */

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.vaibhavtraders.bo.InvoiceBO;
import com.vaibhavtraders.dao.CountryRepository;
import com.vaibhavtraders.dao.CustomerRepository;
import com.vaibhavtraders.dao.DeliveryModeRepository;
import com.vaibhavtraders.dao.StateRepository;
import com.vaibhavtraders.dto.CustomerDTO;
import com.vaibhavtraders.dto.DeliveryModeDTO;
import com.vaibhavtraders.dto.InvoiceDTO;
import com.vaibhavtraders.entity.Country;
import com.vaibhavtraders.entity.Customer;
import com.vaibhavtraders.entity.DeliveryMode;
import com.vaibhavtraders.entity.Invoice;
import com.vaibhavtraders.entity.State;
import com.vaibhavtraders.exception.GeneralException;
import com.vaibhavtraders.requests.InvoiceCreationUpdationRequest;
import com.vaibhavtraders.response.ResponseObject;

@Service
public class InvoiceService {

	@Autowired
	InvoiceBO invoiceBO;

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	DeliveryModeRepository deliveryModeRepository;

	static Logger logger = Logger.getLogger(InvoiceService.class);

	public ResponseObject createInvoiceWithCustomerAndDeliveryMode(
			InvoiceCreationUpdationRequest invoiceCreationUpdationRequest) {
		logger.info("createInvoiceWithCustomerAndDeliveryMode triggered. Inside Invoice Service");
		ResponseObject responseObject = new ResponseObject();
		Long customerId = invoiceCreationUpdationRequest.getCustomerDTO().getCustomerId();
		Long deliveryModeId = invoiceCreationUpdationRequest.getDeliveryModeDTO().getDeliveryModeID();
		try {
			Customer customer = customerRepository.findById(customerId).orElse(null);
			DeliveryMode deliveryMode = deliveryModeRepository.findById(deliveryModeId).orElse(null);

			if (customer == null || deliveryMode == null) {
				responseObject.setFailureMessage("Customer or delivery mode not found.");
				return responseObject;
			}

			Invoice invoice = new Invoice();
			invoice.setCustomer(customer);
			invoice.setDeliveryMode(deliveryMode);
			invoice.setDate(invoiceCreationUpdationRequest.getInvoiceDTO().getDate());
			invoice.setChallanNo(invoiceCreationUpdationRequest.getInvoiceDTO().getChallanNo());
			invoice.setChallanDate(invoiceCreationUpdationRequest.getInvoiceDTO().getChallanDate());
			invoice.setPoNo(invoiceCreationUpdationRequest.getInvoiceDTO().getPoNo());
			invoice.setPoDate(invoiceCreationUpdationRequest.getInvoiceDTO().getPoDate());
			invoice.setLrNo(invoiceCreationUpdationRequest.getInvoiceDTO().getLrNo());
			invoice.seteWayNo(invoiceCreationUpdationRequest.getInvoiceDTO().geteWayNo());

			Invoice createdInvoice = invoiceBO.createInvoiceWithCustomerAndDeliveryMode(invoice, customer,
					deliveryMode);

			responseObject.setSuccessMessage("Invoice created successfully");
			responseObject.setInvoiceDTO(convertInvoiceEntityToDTO(createdInvoice));
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while creating the invoice: " + e.getMessage());
		}
		return responseObject;
	}

	public ResponseObject updateInvoiceWithCustomerAndDeliveryMode(
			@RequestBody InvoiceCreationUpdationRequest invoiceCreationUpdationRequest) {
		logger.info("updateInvoiceWithCustomerAndDeliveryMode triggered. Inside Invoice Service.");
		ResponseObject responseObject = new ResponseObject();
		Long customerId = invoiceCreationUpdationRequest.getCustomerDTO().getCustomerId();
		Long deliveryModeId = invoiceCreationUpdationRequest.getDeliveryModeDTO().getDeliveryModeID();
		Customer customer = null;
		DeliveryMode deliveryMode = null;
		try {
			if (customerId != null) {
				customer = customerRepository.findById(customerId).orElse(null);
			}
			if (deliveryModeId != null) {
				deliveryMode = deliveryModeRepository.findById(deliveryModeId).orElse(null);
			}

			Long invoiceId = invoiceCreationUpdationRequest.getInvoiceId();

			Invoice existingInvoice = invoiceBO.getInvoiceById(invoiceId);

			if (existingInvoice == null) {
				responseObject.setFailureMessage("Invoice not found.");
				return responseObject;
			}

			if (customer != null) {
				existingInvoice.setCustomer(customer);
			}
			if (deliveryMode != null) {
				existingInvoice.setDeliveryMode(deliveryMode);
			}
			if (invoiceCreationUpdationRequest.getInvoiceDTO().getDate() != null) {
				existingInvoice.setDate(invoiceCreationUpdationRequest.getInvoiceDTO().getDate());
			}
			if (invoiceCreationUpdationRequest.getInvoiceDTO().getChallanNo() != null
					&& !invoiceCreationUpdationRequest.getInvoiceDTO().getChallanNo().equals("")) {
				existingInvoice.setChallanNo(invoiceCreationUpdationRequest.getInvoiceDTO().getChallanNo());
			}
			if (invoiceCreationUpdationRequest.getInvoiceDTO().getChallanDate() != null) {
				existingInvoice.setChallanDate(invoiceCreationUpdationRequest.getInvoiceDTO().getChallanDate());
			}
			if (invoiceCreationUpdationRequest.getInvoiceDTO().getPoNo() != null
					&& !invoiceCreationUpdationRequest.getInvoiceDTO().getPoNo().equals("")) {
				existingInvoice.setPoNo(invoiceCreationUpdationRequest.getInvoiceDTO().getPoNo());
			}
			if (invoiceCreationUpdationRequest.getInvoiceDTO().getPoDate() != null) {
				existingInvoice.setPoDate(invoiceCreationUpdationRequest.getInvoiceDTO().getPoDate());
			}
			if (invoiceCreationUpdationRequest.getInvoiceDTO().getLrNo() != null
					&& !invoiceCreationUpdationRequest.getInvoiceDTO().getLrNo().equals("")) {
				existingInvoice.setLrNo(invoiceCreationUpdationRequest.getInvoiceDTO().getLrNo());
			}
			if (invoiceCreationUpdationRequest.getInvoiceDTO().geteWayNo() != null
					&& !invoiceCreationUpdationRequest.getInvoiceDTO().geteWayNo().equals("")) {
				existingInvoice.seteWayNo(invoiceCreationUpdationRequest.getInvoiceDTO().geteWayNo());
			}

			Invoice updatedInvoice = invoiceBO.updateInvoiceWithCustomerAndDeliveryMode(existingInvoice, customer,
					deliveryMode);

			responseObject.setSuccessMessage("Invoice updated successfully");
			responseObject.setInvoiceDTO(convertInvoiceEntityToDTO(updatedInvoice));
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while updating the invoice: " + e.getMessage());
		}
		return responseObject;
	}

	public ResponseObject deleteInvoice(@RequestBody InvoiceDTO invoiceDTO) {
		logger.info("deleteInvoice method triggered. Inside InvoiceService.");
		ResponseObject responseObject = new ResponseObject();
		try {
			Invoice invoiceToDelete = convertInvoiceDTOToEntity(invoiceDTO);
			Invoice existingInvoice = invoiceBO.getInvoiceById(invoiceToDelete.getInvoiceNo());

			if (existingInvoice == null) {
				responseObject.setFailureMessage("Invoice not found.");
				return responseObject;
			}

			invoiceBO.deleteInvoice(existingInvoice);

			responseObject.setSuccessMessage("Invoice deleted successfully");
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while deleting the invoice: " + e.getMessage());
		}
		return responseObject;

	}

	public ResponseObject findInvoiceById(Long invoiceId) {
		logger.info("findInvoiceById method triggered. Inside InvoiceService.");
		ResponseObject responseObject = new ResponseObject();
		try {
			if (invoiceId == null) {
				responseObject.setFailureMessage("Invoice Not Found. for given id : " + invoiceId);
				return responseObject;
			}
			Invoice invoice = invoiceBO.getInvoiceById(invoiceId);
			InvoiceDTO invDTO = new InvoiceDTO();
			if(invoice != null) {
				invDTO.setInvoiceItems(invoice.getInvoiceItems());
			}
			responseObject.setInvoiceDTO(invDTO);
			responseObject.setSuccessMessage("Invoice Found with given id : " + invoiceId);
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while finding the Invoice : " + e.getMessage());
		}
		return responseObject;
	}
	
	public ResponseObject findAllInvoices() throws GeneralException {
		logger.info("findAllInvoices method triggered. Inside InvoiceService");
		ResponseObject responseObject = new ResponseObject();
		try {
			List<Invoice> resultInvoices = invoiceBO.findAllInvoices();
			List<InvoiceDTO> resultInvoicesDTOList = new ArrayList<InvoiceDTO>();
			if(resultInvoices != null) {
				responseObject.setSuccessMessage("findAllInvoices method ran successfully!");
				for(Invoice inv : resultInvoices) {
					InvoiceDTO invoiceDTO = new InvoiceDTO();
					invoiceDTO.setInvoiceNo(inv.getInvoiceNo());
					invoiceDTO.setDate(inv.getDate());
					invoiceDTO.setChallanNo(inv.getChallanNo());
					invoiceDTO.setChallanDate(inv.getChallanDate());
					invoiceDTO.setPoNo(inv.getPoNo());
					invoiceDTO.setPoDate(inv.getPoDate());
					invoiceDTO.setLrNo(inv.getLrNo());
					invoiceDTO.seteWayNo(inv.geteWayNo());
					invoiceDTO.setTotalTaxableAmount(inv.getTotalTaxableAmount());
					invoiceDTO.setTotalAmount(inv.getTotalAmount());
					invoiceDTO.setDeliveryMode(inv.getDeliveryMode());
					invoiceDTO.setCustomer(inv.getCustomer());
					resultInvoicesDTOList.add(invoiceDTO);
				}
				responseObject.setInvoiceDTOList(resultInvoicesDTOList);
			}
			
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while finding all invoices. Check once.");
		}
		return responseObject;
	}

	public ResponseObject getInvoicesForMonthYear(int year, Month month) throws GeneralException {
		// Logic to find out start date and end date of a given month on basis of year
		// and month.
		ResponseObject responseObject = new ResponseObject();
		List<Invoice> resultInvoices = new ArrayList<Invoice>();
		List<InvoiceDTO> resultInvoicesDTOList = new ArrayList<InvoiceDTO>();
		if(month == null) {
			responseObject.setFailureMessage("Error. Empty Data");
			return responseObject;
		}
		try {
			LocalDate startDate = LocalDate.of(year, month, 1);
			LocalDate endDate = startDate.plusMonths(1).minusDays(1);
			
			resultInvoices = invoiceBO.findByDateBetween(startDate, endDate); 

			if(!resultInvoices.contains(null)) {
				responseObject.setSuccessMessage("getInvoicesForMonthYear method ran successfully!");
				for(Invoice inv : resultInvoices) {
					InvoiceDTO invoiceDTO = new InvoiceDTO();
					invoiceDTO.setInvoiceNo(inv.getInvoiceNo());
					invoiceDTO.setDate(inv.getDate());
					invoiceDTO.setChallanNo(inv.getChallanNo());
					invoiceDTO.setChallanDate(inv.getChallanDate());
					invoiceDTO.setPoNo(inv.getPoNo());
					invoiceDTO.setPoDate(inv.getPoDate());
					invoiceDTO.setLrNo(inv.getLrNo());
					invoiceDTO.seteWayNo(inv.geteWayNo());
					invoiceDTO.setTotalTaxableAmount(inv.getTotalTaxableAmount());
					invoiceDTO.setTotalAmount(inv.getTotalAmount());
					invoiceDTO.setDeliveryMode(inv.getDeliveryMode());
					invoiceDTO.setCustomer(inv.getCustomer());
					resultInvoicesDTOList.add(invoiceDTO);
				}
				responseObject.setInvoiceDTOList(resultInvoicesDTOList);
			}
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while finding all invoices. Check once.");
		}
		return responseObject; 
	}

	// can put the below methods inside a separate class 'ConversionUtil'. and use
	// these whenever required.
	public InvoiceDTO convertInvoiceEntityToDTO(Invoice createdInvoice) {
		// TODO Auto-generated method stub
		if (createdInvoice == null) {
			return null;
		}

		InvoiceDTO invoiceDTO = new InvoiceDTO();
		invoiceDTO.setInvoiceNo(createdInvoice.getInvoiceNo());
		invoiceDTO.setDate(createdInvoice.getDate());
		invoiceDTO.setChallanNo(createdInvoice.getChallanNo());
		invoiceDTO.setChallanDate(createdInvoice.getChallanDate());
		invoiceDTO.setPoNo(createdInvoice.getPoNo());
		invoiceDTO.setPoDate(createdInvoice.getPoDate());
		invoiceDTO.setLrNo(createdInvoice.getLrNo());
		invoiceDTO.seteWayNo(createdInvoice.geteWayNo());

		return invoiceDTO;
	}

	public Invoice convertInvoiceDTOToEntity(InvoiceDTO invoiceDTO) {
		// TODO Auto-generated method stub
		if (invoiceDTO == null) {
			return null;
		}
		Invoice invoiceEntity = new Invoice();
		invoiceEntity.setInvoiceNo(invoiceDTO.getInvoiceNo());
		/*invoiceEntity.setDate(invoiceDTO.getDate());
		invoiceEntity.setChallanNo(invoiceDTO.getChallanNo());
		invoiceEntity.setChallanDate(invoiceDTO.getChallanDate());
		invoiceEntity.setPoNo(invoiceDTO.getPoNo());
		invoiceEntity.setPoDate(invoiceDTO.getPoDate());
		invoiceEntity.setLrNo(invoiceDTO.getLrNo());
		invoiceEntity.seteWayNo(invoiceDTO.geteWayNo());*/

		return invoiceEntity;
	}

	public DeliveryMode convertDeliveryModeDTOToEntity(DeliveryModeDTO deliveryModeDTO) {
		// TODO Auto-generated method stub
		if (deliveryModeDTO == null) {
			return null;
		}

		DeliveryMode deliveryMode = new DeliveryMode();

		deliveryMode.setDeliveryModeID(deliveryModeDTO.getDeliveryModeID());
		deliveryMode.setModeName(deliveryModeDTO.getModeName());

		return deliveryMode;
	}

	public Customer convertCustomerDTOToEntity(CustomerDTO customerDTO) {
		// TODO Auto-generated method stub
		if (customerDTO == null) {
			return null;
		}

		Customer customer = new Customer();
		customer.setCustomerId(customerDTO.getCustomerId());
		customer.setGstin(customerDTO.getGstin());
		customer.setCompanyName(customerDTO.getCompanyName());
		customer.setContactPerson(customerDTO.getContactPerson());
		customer.setContactNo(customerDTO.getContactNo());
		customer.setEmail(customerDTO.getEmail());
		customer.setPan(customerDTO.getPan());
		customer.setAddressLine1(customerDTO.getAddressLine1());
		customer.setAddressLine2(customerDTO.getAddressLine2());
		customer.setLandmark(customerDTO.getLandmark());
		/*
		 * customer.setCountry(customerDTO.getCountry());
		 * customer.setState(customerDTO.getState());
		 */
		customer.setCity(customerDTO.getCity());
		customer.setPincode(customerDTO.getPincode());
		customer.setWebsite(customerDTO.getWebsite());
		customer.setNote(customerDTO.getNote());

		// Set the Country entity based on the provided ID
		Country country = countryRepository.findById(customerDTO.getCountry().getCountryID()).orElse(null);
		customer.setCountry(country);

		// Set the State entity based on the provided ID
		State state = stateRepository.findById(customerDTO.getState().getStateID()).orElse(null);
		customer.setState(state);

		return customer;
	}
}
