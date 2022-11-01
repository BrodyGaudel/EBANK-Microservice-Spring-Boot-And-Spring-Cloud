package com.brody.gestiondescomptes.service;

import java.util.List;

import com.brody.gestiondescomptes.dto.CompteDTO;
import com.brody.gestiondescomptes.exception.ClientAlreadyHaveAccountException;
import com.brody.gestiondescomptes.exception.ClientNotExistException;
import com.brody.gestiondescomptes.exception.CompteNotFoundException;

public interface CompteService {
	
	CompteDTO save(CompteDTO compteDTO) throws ClientAlreadyHaveAccountException, ClientNotExistException;
	CompteDTO create(CompteDTO compteDTO) throws ClientAlreadyHaveAccountException, ClientNotExistException;
	CompteDTO update(String rib, CompteDTO compteDTO);
	CompteDTO updateDevise(String rib, String newDevise);
	
	CompteDTO findByRib(String rib) throws CompteNotFoundException;
	CompteDTO findByClientId(String clientId);
	List<CompteDTO> findAll();
	
	void deleteByRib(String rib);
	void deleteByClientId(String clientId);
	void deleteAll();
	
	CompteDTO activate(String rib);
	CompteDTO suspend(String rib);
	CompteDTO desactivate(String rib);

}
