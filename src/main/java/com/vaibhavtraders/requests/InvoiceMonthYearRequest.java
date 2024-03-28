package com.vaibhavtraders.requests;

import java.time.Month;

public class InvoiceMonthYearRequest {
	int year;
	Month month;
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Month getMonth() {
		return month;
	}
	public void setMonth(Month month) {
		this.month = month;
	}
}
