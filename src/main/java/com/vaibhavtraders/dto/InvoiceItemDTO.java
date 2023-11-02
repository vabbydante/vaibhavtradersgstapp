package com.vaibhavtraders.dto;

import com.vaibhavtraders.entity.Invoice;
import com.vaibhavtraders.entity.Product;

public class InvoiceItemDTO {
	private Long id;
	private Invoice invoice;
	private Product product;
    private int quantity;
    private Double taxableAmount;
    private Double totalAmount;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Double getTaxableAmount() {
		return taxableAmount;
	}
	public void setTaxableAmount(Double taxableAmount) {
		this.taxableAmount = taxableAmount;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	@Override
	public String toString() {
		return "InvoiceItemDTO [id=" + id + ", invoice=" + invoice + ", product=" + product + ", quantity=" + quantity
				+ ", taxableAmount=" + taxableAmount + ", totalAmount=" + totalAmount + "]";
	}
    
}
