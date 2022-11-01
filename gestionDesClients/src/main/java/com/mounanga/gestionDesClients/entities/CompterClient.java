package com.mounanga.gestionDesClients.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CompterClient {
	
	@Id
	private Long id;

	public CompterClient(Long id) {
		this.id = id;
	}

	public CompterClient() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CompterProduct [id=" + id + "]";
	}

}
