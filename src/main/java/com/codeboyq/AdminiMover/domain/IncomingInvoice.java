package com.codeboyq.AdminiMover.domain;

public class IncomingInvoice {

	String invoiceDateString;
	String customer;
	
	public IncomingInvoice(String invoiceDateString, String customer) {
		super();
		this.invoiceDateString = invoiceDateString;
		this.customer = customer;
	}
	
	public String getFilename() {
		return String.format("%s %s.pdf", invoiceDateString, customer);
	}
	
}
