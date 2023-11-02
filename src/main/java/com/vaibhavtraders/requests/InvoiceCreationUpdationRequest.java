package com.vaibhavtraders.requests;

import com.vaibhavtraders.dto.CustomerDTO;
import com.vaibhavtraders.dto.DeliveryModeDTO;
import com.vaibhavtraders.dto.InvoiceDTO;
import com.vaibhavtraders.dto.InvoiceItemDTO;

public class InvoiceCreationUpdationRequest {
	private Long invoiceId;
	private InvoiceDTO invoiceDTO;
	private CustomerDTO customerDTO;
	private DeliveryModeDTO deliveryModeDTO;
	private InvoiceItemDTO invoiceItemDTO;

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public InvoiceDTO getInvoiceDTO() {
		return invoiceDTO;
	}

	public void setInvoiceDTO(InvoiceDTO invoiceDTO) {
		this.invoiceDTO = invoiceDTO;
	}

	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	public DeliveryModeDTO getDeliveryModeDTO() {
		return deliveryModeDTO;
	}

	public void setDeliveryModeDTO(DeliveryModeDTO deliveryModeDTO) {
		this.deliveryModeDTO = deliveryModeDTO;
	}

	public InvoiceItemDTO getInvoiceItemDTO() {
		return invoiceItemDTO;
	}

	public void setInvoiceItemDTO(InvoiceItemDTO invoiceItemDTO) {
		this.invoiceItemDTO = invoiceItemDTO;
	}

}
