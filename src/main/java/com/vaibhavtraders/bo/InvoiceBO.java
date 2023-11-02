package com.vaibhavtraders.bo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaibhavtraders.dao.InvoiceRepository;
import com.vaibhavtraders.dto.InvoiceDTO;
import com.vaibhavtraders.entity.Customer;
import com.vaibhavtraders.entity.DeliveryMode;
import com.vaibhavtraders.entity.Invoice;
import com.vaibhavtraders.entity.InvoiceItem;
import com.vaibhavtraders.entity.Product;
import com.vaibhavtraders.exception.GeneralException;

/**
 * @author Vaibhav Gupta
 *
 */
@Component
public class InvoiceBO {

	@Autowired
	InvoiceRepository invoiceRepository;

	public Invoice getInvoiceById(Long invoiceId) throws GeneralException {
		try {
			// Retrieve the invoice by its ID
			return invoiceRepository.findById(invoiceId).orElse(null);
		} catch (Exception e) {
			throw new GeneralException("Error while fetching the invoice by ID: " + invoiceId);
		}
	}
	
	public List<Invoice> findAllInvoices() throws GeneralException {
		try {
			return invoiceRepository.findAll();
		} catch (Exception e) {
			throw new GeneralException("Error while fetching All Invoices.");
		}
	}
	
	public Invoice saveInvoice(Invoice invoice) throws GeneralException {
		Invoice inv = new Invoice();
		try {
			if(invoice == null) {
				throw new GeneralException();
			}
			inv.setInvoiceNo(invoice.getInvoiceNo());
			inv.setCustomer(invoice.getCustomer());
			inv.setDeliveryMode(invoice.getDeliveryMode());
			inv.setDate(invoice.getDate());
			inv.setChallanNo(invoice.getChallanNo());
			inv.setChallanDate(invoice.getChallanDate());
			inv.setPoNo(invoice.getPoNo());
			inv.setPoDate(invoice.getPoDate());
			inv.setLrNo(invoice.getLrNo());
			inv.seteWayNo(invoice.geteWayNo());
			inv.setTotalAmount(invoice.getTotalAmount());
			inv.setTotalTaxableAmount(invoice.getTotalTaxableAmount());
			return invoiceRepository.save(inv);
		} catch (GeneralException e) {
			throw new GeneralException("Error while saving the invoice after adding Invoice Item in BO layer.");
		}
	}

	public Invoice createInvoiceWithCustomerAndDeliveryMode(Invoice invoice, Customer customer,
			DeliveryMode deliveryMode) throws GeneralException {
		Invoice inv = new Invoice();
		try {
			if (invoice == null || customer == null) {
				throw new GeneralException("Invalid invoice, customer, or delivery mode data.");
			}

			// Set customer and delivery mode
			inv.setCustomer(customer);
			inv.setDeliveryMode(deliveryMode);

			// Ensure you set other properties from the original invoice
			inv.setDate(invoice.getDate());
			inv.setChallanNo(invoice.getChallanNo());
			inv.setChallanDate(invoice.getChallanDate());
			inv.setPoNo(invoice.getPoNo());
			inv.setPoDate(invoice.getPoDate());
			inv.setLrNo(invoice.getLrNo());
			inv.seteWayNo(invoice.geteWayNo());

			// Calculate and set total amounts
			inv = recalculateInvoiceTotal(invoice);

			// Save the updated invoice to the database
			return invoiceRepository.save(inv);
		} catch (GeneralException e) {
			// log.error("Error while creating an invoice with customer and delivery mode",
			// e);
			throw new GeneralException("Error while creating an invoice with customer and delivery mode.");
		}
	}

	public Invoice updateInvoiceWithCustomerAndDeliveryMode(Invoice invoice, Customer customer,
			DeliveryMode deliveryMode) throws GeneralException {
		try {
			if (invoice == null || customer == null) {
				throw new IllegalArgumentException("Invalid invoice, customer, or delivery mode data.");
			}

			Invoice existingInvoice = invoiceRepository.findById(invoice.getInvoiceNo()).orElse(null);
			if (existingInvoice == null) {
				throw new GeneralException("Invoice not found.");
			}

			existingInvoice.setCustomer(customer);
			existingInvoice.setDeliveryMode(deliveryMode);
			// Ensure you set other properties from the original invoice
			existingInvoice.setDate(invoice.getDate());
			existingInvoice.setChallanNo(invoice.getChallanNo());
			existingInvoice.setChallanDate(invoice.getChallanDate());
			existingInvoice.setPoNo(invoice.getPoNo());
			existingInvoice.setPoDate(invoice.getPoDate());
			existingInvoice.setLrNo(invoice.getLrNo());
			existingInvoice.seteWayNo(invoice.geteWayNo());

			return invoiceRepository.save(existingInvoice);
		} catch (GeneralException e) {
			// log.error("Error while updating an invoice with customer and delivery mode",
			// e);
			throw new GeneralException("Error while updating an invoice with customer and delivery mode.");
		}
	}

	public void deleteInvoice(Invoice invoice) throws GeneralException {
		try {
			if (invoice == null) {
				throw new GeneralException("Invalid invoice data.");
			}

			invoiceRepository.delete(invoice);
		} catch (Exception e) {
			// log.error("Error while deleting an invoice", e);
			throw new GeneralException("Error while deleting an invoice.");
		}
	}

	public Invoice getInvoiceWithDetails(Long invoiceId) throws GeneralException {
		try {
			// Retrieve the invoice by its ID
			Invoice invoice = invoiceRepository.findById(invoiceId).orElse(null);

			if (invoice == null) {
				throw new GeneralException("Invoice not found.");
			}

			// Load associated customer and delivery mode
			// You can adjust this part based on your entity relationships
			Customer customer = invoice.getCustomer();
			DeliveryMode deliveryMode = invoice.getDeliveryMode();

			// Load invoice items associated with this invoice
			List<InvoiceItem> invoiceItems = invoice.getInvoiceItems();

			// Calculate any additional details you need

			// Set the customer, delivery mode, and invoice items on the invoice
			invoice.setCustomer(customer);
			invoice.setDeliveryMode(deliveryMode);
			invoice.setInvoiceItems(invoiceItems);

			return invoice;
		} catch (Exception e) {
			// log.error("Error while fetching an invoice with details", e);
			throw new GeneralException("Error while fetching an invoice with details.");
		}
	}

	public Invoice removeProductFromInvoice(Invoice invoice, InvoiceItem itemToRemove) throws GeneralException {
		try {
			if (invoice == null || itemToRemove == null) {
				throw new GeneralException("Invalid invoice or item data.");
			}
			invoice.getInvoiceItems().remove(itemToRemove);
			invoice = recalculateInvoiceTotal(invoice);
			return invoiceRepository.save(invoice);
		} catch (GeneralException e) {
			throw new GeneralException("Error while removing product from the Invoice.");
		}
	}

	public Invoice addProductToInvoice(Invoice invoice, InvoiceItem itemToAdd) throws GeneralException {
		try {
			if (invoice == null || itemToAdd == null) {
				throw new GeneralException("Invalid invoice or item data.");
			}
			invoice.getInvoiceItems().add(itemToAdd);
			invoice = recalculateInvoiceTotal(invoice);
			return invoiceRepository.save(invoice);
		} catch (GeneralException e) {
			throw new GeneralException("Error while adding a product to the invoice.");
		}
	}

	/*
	 * Method to calculate Invoice Total and save it into the DB 
	 */
	public Invoice recalculateInvoiceTotal(Invoice invoice) {
		if (invoice.getInvoiceItems() == null) {
			// Initialize the invoiceItems list if it's null
			invoice.setInvoiceItems(new ArrayList<InvoiceItem>());
		}

		List<InvoiceItem> invoiceItems = invoice.getInvoiceItems();
		double totalTaxableAmount = 0.0;
		double totalAmount = 0.0;

		for (InvoiceItem item : invoiceItems) {
			Product product = item.getProduct();
			int quantity = item.getQuantity();
			double unitPrice = product.getSellPrice();
			double taxableAmount = quantity * unitPrice;
			totalTaxableAmount += taxableAmount;
			double gstAmount = (taxableAmount * product.getGstPercentage()) / 100;
			double totalProductAmount = taxableAmount + gstAmount;
			item.setTaxableAmount(taxableAmount);
			item.setTotalAmount(totalProductAmount);
			totalAmount += totalProductAmount;
		}

		// Set the total amounts directly on the invoice object
		invoice.setTotalTaxableAmount(totalTaxableAmount);
		invoice.setTotalAmount(totalAmount);

		return invoice;
	}

	public List<Invoice> findByDateBetween(LocalDate startDate, LocalDate endDate) throws GeneralException {
		List<Invoice> invoicesByDateBetween = null;
		try {
			invoicesByDateBetween = invoiceRepository.findInvoicesByDateBetween(startDate,
					endDate);
			if (invoicesByDateBetween == null) {
				throw new GeneralException("No invoices found in the given date range.");
			}
		} catch (GeneralException e) {
			throw new GeneralException("Error in InvoiceBO while fetching Invoices by Date Range.");
		}
		return invoicesByDateBetween;
	}

}
