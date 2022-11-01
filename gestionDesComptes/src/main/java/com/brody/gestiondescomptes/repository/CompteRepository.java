package com.brody.gestiondescomptes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brody.gestiondescomptes.entities.Compte;

public interface CompteRepository extends JpaRepository<Compte, String> {

}
