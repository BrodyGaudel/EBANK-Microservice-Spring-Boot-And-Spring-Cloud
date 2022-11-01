package com.brody.gestiondescomptes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brody.gestiondescomptes.entities.CompterCompte;


public interface CompterCompteRepository extends JpaRepository<CompterCompte, Long> {

}
