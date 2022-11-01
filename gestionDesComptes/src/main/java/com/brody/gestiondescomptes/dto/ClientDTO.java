package com.brody.gestiondescomptes.dto;

import java.util.Date;

import com.brody.gestiondescomptes.enums.Sexe;


public class ClientDTO {
	
	private String id;
	private String prenom;
	private String nom;
	private String lieuxDeNaissance;
	private String nationalite;
	private String cin;
	private Sexe sexe;
	private Date dateDeNaissance;
	private String email;
	private Integer phone;
	private String motdePasse;
	
	public ClientDTO() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getLieuxDeNaissance() {
		return lieuxDeNaissance;
	}

	public void setLieuxDeNaissance(String lieuxDeNaissance) {
		this.lieuxDeNaissance = lieuxDeNaissance;
	}

	public String getNationalite() {
		return nationalite;
	}

	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public Sexe getSexe() {
		return sexe;
	}

	public void setSexe(Sexe sexe) {
		this.sexe = sexe;
	}

	public Date getDateDeNaissance() {
		return dateDeNaissance;
	}

	public void setDateDeNaissance(Date dateDeNaissance) {
		this.dateDeNaissance = dateDeNaissance;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public String getMotdePasse() {
		return motdePasse;
	}

	public void setMotdePasse(String motdePasse) {
		this.motdePasse = motdePasse;
	}

	@Override
	public String toString() {
		return "ClientDTO [id=" + id + ", prenom=" + prenom + ", nom=" + nom + ", lieuxDeNaissance=" + lieuxDeNaissance
				+ ", nationalite=" + nationalite + ", cin=" + cin + ", sexe=" + sexe + ", dateDeNaissance="
				+ dateDeNaissance + ", email=" + email + ", phone=" + phone + "]";
	}

}
