package com.vaibhavtraders.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaibhavtraders.bo.InvoiceBO;
import com.vaibhavtraders.bo.InvoiceItemBO;
import com.vaibhavtraders.bo.ProductBO;
import com.vaibhavtraders.dao.InvoiceRepository;
import com.vaibhavtraders.entity.Invoice;
import com.vaibhavtraders.entity.InvoiceItem;
import com.vaibhavtraders.entity.Product;
import com.vaibhavtraders.exception.GeneralException;
import com.vaibhavtraders.response.ResponseObject;

@Service
public class InvoiceItemService {

	@Autowired
	InvoiceItemBO invoiceItemBO;

	@Autowired
	InvoiceBO invoiceBO;

	@Autowired
	ProductBO productBO; // Not in use, remove later.

	public ResponseObject createInvoiceItems(Long invoiceId, List<InvoiceItem> invoiceItems) throws GeneralException {
	    // Retrieve the corresponding invoice from the database
	    Invoice invoice = null;
	    ResponseObject responseObject = new ResponseObject();
	    ArrayList<String> taxableAndTotalList = new ArrayList<String>();

	    try {
	        invoice = invoiceBO.getInvoiceById(invoiceId);

	        if (invoice == null) {
	            responseObject.setFailureMessage("Error while retrieving corresponding invoice for Invoice Item Id: " + invoiceId);
	            return responseObject;
	        }

	        Double totalTaxableAmount = 0.0;
	        Double totalAmount = 0.0;

	        for (InvoiceItem invoiceItem : invoiceItems) {
	            Product product = productBO.findProduct(invoiceItem.getProduct().getProductID());
	            int quantity = invoiceItem.getQuantity();
	            Double unitPrice = product.getSellPrice();
	            Double taxableAmount = quantity * unitPrice;
	            Double gstAmount = (taxableAmount * product.getGstPercentage()) / 100;
	            Double totalProductAmount = taxableAmount + gstAmount;

	            taxableAndTotalList.add(taxableAmount + "-" + totalProductAmount);

	            totalTaxableAmount += taxableAmount;
	            totalAmount += totalProductAmount;
	        }

	        // Update the invoice's totalTaxableAmount and totalAmount
	        if (invoice.getTotalTaxableAmount() != null) {
	            totalTaxableAmount += invoice.getTotalTaxableAmount();
	        }
	        if (invoice.getTotalAmount() != null) {
	            totalAmount += invoice.getTotalAmount();
	        }

	        invoice.setTotalTaxableAmount(totalTaxableAmount);
	        invoice.setTotalAmount(totalAmount);

	        responseObject.setSuccessMessage("Successfully created Invoice Items for invoice id: " + invoiceId);
	    } catch (GeneralException e) {
	        throw new GeneralException("Error in createInvoiceItems under InvoiceItemService.");
	    }

	    // Set the invoice for each invoice item to establish the association
	    for (InvoiceItem invoiceItem : invoiceItems) {
	        invoiceItem.setInvoice(invoice);
	    }

	    // Save each invoice item with its individual calculation
	    for (int i = 0; i < invoiceItems.size(); i++) {
	        String[] parts = taxableAndTotalList.get(i).split("-");
	        if (parts.length == 2) {
	            Double taxableAmount = Double.valueOf(parts[0]);
	            Double totalProductAmount = Double.valueOf(parts[1]);

	            InvoiceItem savedInvoiceItem = invoiceItemBO.createInvoiceItemForInvoice(invoice, invoiceItems.get(i), totalProductAmount, taxableAmount);

	            // You can perform additional operations with savedInvoiceItem if needed
	        } else {
	            System.err.println("Invalid item format: " + taxableAndTotalList.get(i));
	        }
	    }

	    // Save the updated invoice
	    invoiceBO.saveInvoice(invoice);

	    // You can include additional logic or response handling here

	    return responseObject;
	}


	public ResponseObject updateInvoiceItem(Long invoiceId, InvoiceItem invoiceItem) throws GeneralException {
		Invoice invoice = null;
		ResponseObject responseObject = new ResponseObject();
		try {
			invoice = invoiceBO.getInvoiceById(invoiceId);
			if (invoice == null) {
				responseObject.setFailureMessage(
						"Error while retrieving corresponding invoice for Invoice Item Id : " + invoiceId);
			}
			responseObject.setSuccessMessage("Successfully created Invoice Items for Invoice ID : " + invoiceId);
		} catch (GeneralException e) {
			throw new GeneralException("Error in updateInvoiceItem under InvoiceItemService.");
		}

		InvoiceItem invoiceItemResult = invoiceItemBO.updateInvoiceItemForInvoice(invoiceItem);
		responseObject.setInvoiceItem(invoiceItemResult);
		return responseObject;
	}

	public ResponseObject deleteInvoiceItem(Long invoiceId, InvoiceItem invoiceItem) throws GeneralException {
		Invoice invoice = null;
		ResponseObject responseObject = new ResponseObject();
		try {
			invoice = invoiceBO.getInvoiceById(invoiceId);
			if (invoice == null) {
				responseObject.setFailureMessage(
						"Error while retrieving corresponding invoice for Invoice Item ID : " + invoiceItem.getId());
			}
			invoiceItemBO.deleteInvoiceItemForInvoice(invoiceItem.getId());
			// to implement the logic of recalculating the invoice totals, when we delete an invoice item.
			this.recalculateInvoiceTotals(invoice, invoiceItem);
			responseObject.setSuccessMessage(
					"Successfully deleted Invoice Item : " + invoiceItem.getId() + " for Invoice ID : " + invoiceId);
		} catch (GeneralException e) {
			throw new GeneralException("Error in deleteInvoiceItem under InvoiceItemService.");
		}
		return responseObject;
	}

	private void recalculateInvoiceTotals(Invoice invoice, InvoiceItem invoiceItem) throws GeneralException {
		// TODO Auto-generated method stub
		Invoice inv = invoice;
		if(inv != null) {
			try {
				if(!(inv.getTotalAmount() == 0 || inv.getTotalTaxableAmount() == 0)) {
					inv.setTotalAmount(inv.getTotalAmount() - invoiceItem.getTotalAmount());
					inv.setTotalTaxableAmount(inv.getTotalTaxableAmount() - invoiceItem.getTaxableAmount());
					invoiceBO.saveInvoice(inv);
				}
			} catch(GeneralException e) {
				throw new GeneralException("Error in recalculateInvoiceTotals method under InvoiceItemService.");
			}
		}
	}


	public List<InvoiceItem> findInvoiceItemsByInvoice(Long invoiceId) throws GeneralException {
		Invoice invoice = invoiceBO.getInvoiceById(invoiceId);
		if (invoice == null) {
			throw new GeneralException("Invoice not found.");
		}
		return invoiceItemBO.findInvoiceItemsByInvoice(invoice);
	}

}
