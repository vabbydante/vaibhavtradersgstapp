package com.vaibhavtraders.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long invoiceNo;

	private LocalDate date;
	private String challanNo;
	private LocalDate challanDate;
	private String poNo;
	private LocalDate poDate;
	private String lrNo;
	private String eWayNo;
    private Double totalTaxableAmount;
    private Double totalAmount;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "delivery_mode_id")
	private DeliveryMode deliveryMode;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@OneToMany(mappedBy = "invoice", fetch = FetchType.EAGER)
    private List<InvoiceItem> invoiceItems;
	
	public Double getTotalTaxableAmount() {
		return totalTaxableAmount;
	}

	public void setTotalTaxableAmount(Double totalTaxableAmount) {
		this.totalTaxableAmount = totalTaxableAmount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<InvoiceItem> getInvoiceItems() {
		return invoiceItems;
	}

	public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
		this.invoiceItems = invoiceItems;
	}

	public Long getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(Long invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getChallanNo() {
		return challanNo;
	}

	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}

	public LocalDate getChallanDate() {
		return challanDate;
	}

	public void setChallanDate(LocalDate challanDate) {
		this.challanDate = challanDate;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public LocalDate getPoDate() {
		return poDate;
	}

	public void setPoDate(LocalDate poDate) {
		this.poDate = poDate;
	}

	public String getLrNo() {
		return lrNo;
	}

	public void setLrNo(String lrNo) {
		this.lrNo = lrNo;
	}

	public String geteWayNo() {
		return eWayNo;
	}

	public void seteWayNo(String eWayNo) {
		this.eWayNo = eWayNo;
	}

	public DeliveryMode getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(DeliveryMode deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
