package com.brody.gestiondescomptes.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CompterCompte {
	

	@Id
	private Long id;

	public CompterCompte(Long id) {
		this.id = id;
	}

	public CompterCompte() {
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
