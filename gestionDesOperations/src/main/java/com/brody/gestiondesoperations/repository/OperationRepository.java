package com.brody.gestiondesoperations.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.brody.gestiondesoperations.entities.Operation;

public interface OperationRepository extends JpaRepository<Operation, String> {
	
	List<Operation> findByCompteId(String accountId);
	
	Page<Operation> findByCompteIdOrderByOperationDateDesc(String accountId, Pageable pageable);

}
