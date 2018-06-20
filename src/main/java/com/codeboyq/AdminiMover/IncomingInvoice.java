package com.codeboyq.AdminiMover;

public class IncomingInvoice {

	String invoiceDateString;
	String customer;
	
	public IncomingInvoice(String invoiceDateString, String customer) {
		super();
		this.invoiceDateString = invoiceDateString;
		this.customer = customer;
	}
	
	public String getFilename() {
		return invoiceDateString + " " + customer + ".pdf";
	}
	
}
