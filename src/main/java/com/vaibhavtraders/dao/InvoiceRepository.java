package com.vaibhavtraders.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vaibhavtraders.entity.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>{
	
	@Query(value = "Select i from Invoice i where i.date between :date1 AND :date2")
	List<Invoice> findInvoicesByDateBetween(@Param("date1") LocalDate date1, @Param("date2") LocalDate date2);
}
