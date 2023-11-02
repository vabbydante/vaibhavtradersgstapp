package com.vaibhavtraders.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vaibhavtraders.entity.Invoice;
import com.vaibhavtraders.entity.InvoiceItem;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long>{
	// Defining a custom query method to retrieve invoice items by invoice
    List<InvoiceItem> findByInvoice(Invoice invoice);
}
