package com.vaibhavtraders.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaibhavtraders.dao.InvoiceItemRepository;
import com.vaibhavtraders.entity.Invoice;
import com.vaibhavtraders.entity.InvoiceItem;
import com.vaibhavtraders.exception.GeneralException;

@Component
public class InvoiceItemBO {

	@Autowired
	InvoiceItemRepository invoiceItemRepository;
	
	public InvoiceItem createInvoiceItemForInvoice(Invoice invoice, InvoiceItem invoiceItem, Double totalAmount, Double totalTaxableAmount) throws GeneralException {
        try {
            if (invoiceItem == null) {
                throw new IllegalArgumentException("Invalid invoice item data.");
            }

            if (invoice == null || invoice.getInvoiceNo() == null) {
                throw new IllegalArgumentException("Invalid invoice data.");
            }

            invoiceItem.setInvoice(invoice);
            invoiceItem.setTotalAmount(totalAmount);
            invoiceItem.setTaxableAmount(totalTaxableAmount);
            return invoiceItemRepository.save(invoiceItem);
        } catch (Exception e) {
            //log.error("Error while creating an invoice item for an invoice", e);
            throw new GeneralException("Error while creating an invoice item for an invoice.");
        }
    }
    
    public List<InvoiceItem> saveAllInvoiceItems(List<InvoiceItem> invoiceItems, Double totalAmount, Double totalTaxableAmount) throws GeneralException {
        try {
            if (invoiceItems == null || invoiceItems.isEmpty()) {
                throw new IllegalArgumentException("Invoice items list is empty or null.");
            }

            // Iterate through the list of invoice items and save each one
            List<InvoiceItem> savedInvoiceItems = new ArrayList<>();
            for (InvoiceItem invoiceItem : invoiceItems) {
                savedInvoiceItems.add(createInvoiceItemForInvoice(invoiceItem.getInvoice(), invoiceItem, totalAmount, totalTaxableAmount));
            }

            return savedInvoiceItems;
        } catch (Exception e) {
            // Handle exceptions or log errors here
            throw new GeneralException("Error while saving invoice items: " + e.getMessage());
        }
    }

    
    public InvoiceItem updateInvoiceItemForInvoice(InvoiceItem invoiceItem) throws GeneralException {
        try {
            if (invoiceItem == null || invoiceItem.getInvoice() == null || invoiceItem.getInvoice().getInvoiceNo() == null) {
                throw new IllegalArgumentException("Invalid invoice item data.");
            }

            InvoiceItem existingInvoiceItem = invoiceItemRepository.findById(invoiceItem.getId()).orElse(null);
            if (existingInvoiceItem == null) {
                throw new GeneralException("Invoice item not found.");
            }

            // You can perform updates to the invoice item here
            existingInvoiceItem.setQuantity(invoiceItem.getQuantity());
            existingInvoiceItem.setProduct(invoiceItem.getProduct());

            return invoiceItemRepository.save(existingInvoiceItem);
        } catch (Exception e) {
            //log.error("Error while updating an invoice item for an invoice", e);
            throw new GeneralException("Error while updating an invoice item for an invoice.");
        }
    }
    
    public void deleteInvoiceItemForInvoice(Long invoiceItemId) throws GeneralException {
        try {
            InvoiceItem invoiceItem = invoiceItemRepository.findById(invoiceItemId).orElse(null);
            if (invoiceItem == null || invoiceItem.getInvoice() == null || invoiceItem.getInvoice().getInvoiceNo() == null) {
                throw new IllegalArgumentException("Invalid invoice item data.");
            }

            invoiceItemRepository.delete(invoiceItem);
        } catch (Exception e) {
            //log.error("Error while deleting an invoice item for an invoice", e);
            throw new GeneralException("Error while deleting an invoice item for an invoice.");
        }
    }
    
    public List<InvoiceItem> findInvoiceItemsByInvoice(Invoice invoice) throws GeneralException {
        try {
            if (invoice == null || invoice.getInvoiceNo() == null) {
                throw new IllegalArgumentException("Invalid invoice data.");
            }

            List<InvoiceItem> invoiceItems = invoiceItemRepository.findByInvoice(invoice);

            if (invoiceItems == null) {
                throw new GeneralException("No invoice items found for the specified invoice.");
            }

            return invoiceItems;
        } catch (Exception e) {
            //log.error("Error while fetching invoice items by invoice", e);
            throw new GeneralException("Error while fetching invoice items by invoice.");
        }
    }
	
	//Commenting out code for further reference. See above working code.
	/*public InvoiceItem insert(InvoiceItem ii) throws GeneralException {
		try {
			if(ii.getQuantity() == 0) {
				throw new GeneralException("Invoice Item cannot be 0.");
			}
			if(ii.getProduct().getName() == null) {
				throw new GeneralException("Name cannot be empty!");
			}
		} catch (GeneralException e) {
			throw new GeneralException("Error while inserting Invoice Item.");
		}
		return invoiceItemRepository.save(ii);
	}
	
	public List<InvoiceItem> findAllInvoiceItems() throws GeneralException {
		List<InvoiceItem> allInvoiceItemsList = new ArrayList<InvoiceItem>();
		try {
			allInvoiceItemsList = invoiceItemRepository.findAll();
			if(allInvoiceItemsList == null) {
				throw new GeneralException("No invoice items found.");
			}
		} catch (GeneralException e) {
			throw new GeneralException("Error while fetching Invoice Items.");
		}
		return allInvoiceItemsList;
	}
	
	public InvoiceItem deleteInvoiceItem (InvoiceItem ii) throws GeneralException {
		try {
			if(ii == null) {
				throw new GeneralException("Param ID InvoiceItem ii is null.");
			}
		} catch (GeneralException e) {
			throw new GeneralException("Error while deleting Specific Invoice Item.");
		}
		invoiceItemRepository.delete(ii);
		System.out.println("Invoice Item with ID : " + ii.getInvoice().getInvoiceNo() +  " Deleted successfully.");
		return ii;
	}*/
}
