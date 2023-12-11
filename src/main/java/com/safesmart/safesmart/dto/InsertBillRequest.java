package com.safesmart.safesmart.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.safesmart.safesmart.model.Dollar;

public class InsertBillRequest {

	private Long id;

	private String amount;

	private String user;

	private LocalDate createdOn;

	private String transactionNumber;
	
    private LocalDateTime withDrawDateTime;
	
	private boolean withDrawStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public LocalDate getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDate createdOn) {
		this.createdOn = createdOn;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public void dollerValidation() {
		List<String> dollars = new ArrayList<String>();
		for (Dollar dollar : Arrays.asList(Dollar.values())) {
			dollars.add(dollar.getDollar());
		}
		
		if (!dollars.contains(this.amount)) {
			throw new RuntimeException("Invalid currency");
		}
		;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public LocalDateTime getWithDrawDateTime() {
		return withDrawDateTime;
	}

	public void setWithDrawDateTime(LocalDateTime withDrawDateTime) {
		this.withDrawDateTime = withDrawDateTime;
	}

	public boolean isWithDrawStatus() {
		return withDrawStatus;
	}

	public void setWithDrawStatus(boolean withDrawStatus) {
		this.withDrawStatus = withDrawStatus;
	}
	

}
