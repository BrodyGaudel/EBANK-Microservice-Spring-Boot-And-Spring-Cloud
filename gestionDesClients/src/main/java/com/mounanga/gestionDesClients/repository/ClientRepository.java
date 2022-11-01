package com.mounanga.gestionDesClients.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.mounanga.gestionDesClients.entities.Client;

public interface ClientRepository extends JpaRepository<Client, String> {
	
	@Query("select c from Client c where c.prenom like :kw")
	List<Client> findByNameContains(@Param("kw") String keywords);
	
	@Query("select c from Client c where c.cin like :kw")
	List<Client> findByCinContains(@Param("kw") String keywords);

}
