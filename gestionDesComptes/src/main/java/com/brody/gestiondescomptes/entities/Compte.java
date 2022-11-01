package com.brody.gestiondescomptes.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.brody.gestiondescomptes.enums.AccountStatus;
import com.brody.gestiondescomptes.enums.Devise;


@Entity
public class Compte {
	
	@Id
	private String rib;
	
	private double solde;
	
	@Enumerated(EnumType.STRING)
	private Devise devise;
	
	@Enumerated(EnumType.STRING)
	private AccountStatus status;
	
	private Date createdAt;
	
	private String clientId;
	
	public Compte(String rib, double solde, Devise devise, AccountStatus status, Date createdAt, String clientId) {
	
		this.rib = rib;
		this.solde = solde;
		this.devise = devise;
		this.status = status;
		this.createdAt = createdAt;
		this.clientId = clientId;
	}

	public Compte() {
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
		return "Compte [rib=" + rib + ", solde=" + solde + ", devise=" + devise + ", status=" + status + ", createdAt="
				+ createdAt + ", clientId=" + clientId + "]";
	}

}
