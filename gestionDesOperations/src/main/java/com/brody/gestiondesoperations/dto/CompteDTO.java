package com.brody.gestiondesoperations.dto;

import com.brody.gestiondesoperations.enums.AccountStatus;
import com.brody.gestiondesoperations.enums.Devise;

import java.util.Date;



public class CompteDTO {
	
	private String rib;
	
	private double solde;
	
	private Devise devise;
	
	private AccountStatus status;
	
	private Date createdAt;
	
	private String clientId;

	public CompteDTO(String rib, double solde, Devise devise, AccountStatus status, Date createdAt, String clientId) {
	
		this.rib = rib;
		this.solde = solde;
		this.devise = devise;
		this.status = status;
		this.createdAt = createdAt;
		this.clientId = clientId;
	}

	public CompteDTO() {
		super();
	}

	public String getRib() {
		return rib;
	}

	public void setRib(String rib) {
		this.rib = rib;
	}

	public double getSolde() {
		return solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}

	public Devise getDevise() {
		return devise;
	}

	public void setDevise(Devise devise) {
		this.devise = devise;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Override
	public String toString() {
		return "CompteDTO [rib=" + rib + ", solde=" + solde + ", devise=" + devise + ", status=" + status
				+ ", createdAt=" + createdAt + ", clientId=" + clientId + "]";
	}
	
	
	
	
	
	
}
