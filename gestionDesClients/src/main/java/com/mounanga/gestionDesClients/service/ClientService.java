package com.mounanga.gestionDesClients.service;

import java.util.List;

import com.mounanga.gestionDesClients.dto.ClientDTO;
import com.mounanga.gestionDesClients.exception.CinAlreadyExistException;
import com.mounanga.gestionDesClients.exception.ClientNotFoundException;
import com.mounanga.gestionDesClients.exception.EmailAlreadyExistException;
import com.mounanga.gestionDesClients.exception.PhoneAlreadyExistException;

public interface ClientService {
	
	ClientDTO findById(String id) throws ClientNotFoundException;
	ClientDTO findByCin(String cin);
	List<ClientDTO> findByName(String name);
	List<ClientDTO> findAll();
	
	ClientDTO save(ClientDTO clientDTO) throws EmailAlreadyExistException, CinAlreadyExistException, PhoneAlreadyExistException;
	ClientDTO update(String id, ClientDTO clientDTO);
	
	void deleteById(String id);
	void deleteByCin(String cin);
	void deleteAll();
	void updatePassword(String id, String oldPassword, String newPassword);

}
