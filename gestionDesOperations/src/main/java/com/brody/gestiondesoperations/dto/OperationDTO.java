package com.brody.gestiondesoperations.dto;

import java.util.Date;

import com.brody.gestiondesoperations.enums.Type;

public class OperationDTO {
	
	private String id;
	private Date operationDate;
	private double amount;
	private Type type;
	private String description;
	private String compteId;
	
	public OperationDTO(String id, Date operationDate, double amount, Type type, String description, String compteId) {
		
		this.id = id;
		this.operationDate = operationDate;
		this.amount = amount;
		this.type = type;
		this.description = description;
		this.compteId = compteId;
	}

	public OperationDTO() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompteId() {
		return compteId;
	}

	public void setCompteId(String compteId) {
		this.compteId = compteId;
	}

	@Override
	public String toString() {
		return "OperationDTO [id=" + id + ", operationDate=" + operationDate + ", amount=" + amount + ", type=" + type
				+ ", description=" + description + ", compteId=" + compteId + "]";
	}
	
}
