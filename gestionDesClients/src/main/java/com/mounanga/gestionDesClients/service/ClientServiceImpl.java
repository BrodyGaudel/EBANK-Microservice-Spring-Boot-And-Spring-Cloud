package com.mounanga.gestionDesClients.service;

import java.util.Collections;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mounanga.gestionDesClients.dto.ClientDTO;
import com.mounanga.gestionDesClients.entities.Client;
import com.mounanga.gestionDesClients.exception.CinAlreadyExistException;
import com.mounanga.gestionDesClients.exception.ClientNotFoundException;
import com.mounanga.gestionDesClients.exception.EmailAlreadyExistException;
import com.mounanga.gestionDesClients.exception.PhoneAlreadyExistException;
import com.mounanga.gestionDesClients.mapping.Mappers;
import com.mounanga.gestionDesClients.repository.ClientRepository;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
	
	private static final Logger log = Logger.getLogger(ClientServiceImpl.class);
	
	private static final String CLIENT_NOT_FOUND = "CUSTOMER NOT FOUND";
	private static final String EMAIL_EXIST = "MAIL IS ALREADY USED BY ANOTHER CUSTOMER";
	private static final String CIN_EXIST = "CIN IS ALREADY USED BY ANOTHER CUSTOMER";
	private static final String PHONE_EXIST = "PHONE IS ALREADY USED BY ANOTHER CUSTOMER";
	private static final String CUSTOMER_NOT_FOUND = "CUSTOMER(S) NOT FOUND : ";
	private static final String CUSTOMER_FOUND = "CUSTOMER(S) FOUND : ";
	private static final String CUSTOMER_SAVED = "CUSTOMER(S) SAVED : ";
	private static final String CUSTOMER_UPDATED = "CUSTOMER(S) UPDATED : ";
	private static final String CUSTOMER_NOT_UPDATED = "CUSTOMER(S) NOT UPDATED : ";
	private static final String CUSTOMER_DELETED = "CUSTOMER(S) DELETED : ";
	private static final String CUSTOMER_NOT_DELETED = "CUSTOMER(S) NOT DELETED : ";
	
	
	private ClientRepository clientRepository;
	private GenerateIdService generatedIdService;
	private Mappers mappers;
	
	

	public ClientServiceImpl(ClientRepository clientRepository, GenerateIdService generatedIdService, Mappers mappers) {
		
		this.clientRepository = clientRepository;
		this.generatedIdService = generatedIdService;
		this.mappers = mappers;
	}

	@Override
	public ClientDTO findById(String id) throws ClientNotFoundException {
		log.info("IN findById(): ");
		Client client = clientRepository.findById(id)
				.orElseThrow(() -> new ClientNotFoundException(CLIENT_NOT_FOUND));
		log.info(CUSTOMER_FOUND);
		return mappers.fromClient(client);
	}

	@Override
	public ClientDTO findByCin(String cin) {
		log.info("IN findByCin()");
		try {
			List<Client> clients = clientRepository.findByCinContains(cin);
			Client client = new Client();
			for(Client c: clients) {
				if(c.getCin().equals(cin)) {
					client = c;
				}
			}
			log.info(CUSTOMER_FOUND);
			return mappers.fromClient(client);
		}catch(Exception e) {
			log.error(CUSTOMER_NOT_FOUND);
			return null;
		}
		
	}


	@Override
	public List<ClientDTO> findAll() {
		log.info("IN findAll()");
		try {
			List<Client> clients = clientRepository.findAll();
			log.info(CUSTOMER_FOUND);
			return mappers.fromListOfClients(clients);
		}catch(Exception e) {
			log.error(CUSTOMER_NOT_FOUND);
			return Collections.emptyList();
		}
	}

	@Override
	public ClientDTO save(ClientDTO clientDTO) throws EmailAlreadyExistException, CinAlreadyExistException, PhoneAlreadyExistException {
		log.info("IN save()");
		Client client = mappers.fromClientDTO(clientDTO);
		
		boolean emailExisteDeja = false;
		boolean phoneExisteDeja = false;
		boolean cinExisteDeja = false;
		List<Client> clients = clientRepository.findAll();
		
		for(Client c: clients) {
			if(c.getCin().equals(client.getCin())) {
				cinExisteDeja = true;
			}
			if(c.getPhone().equals(client.getPhone())) {
				phoneExisteDeja = true;
			}
			if(c.getEmail().equals(client.getEmail())){
				emailExisteDeja = true;
			}
		}
		
		if(emailExisteDeja) {
			throw new EmailAlreadyExistException(EMAIL_EXIST);
		}
		if(cinExisteDeja) {
			throw new CinAlreadyExistException(CIN_EXIST);
		}
		if(phoneExisteDeja) {
			throw new PhoneAlreadyExistException(PHONE_EXIST);
		}
		
		client.setId(generatedIdService.generateClientId(client.getSexe()));
		
		Client savedClient = clientRepository.save(client);
		log.info(CUSTOMER_SAVED);
		savedClient.setMotDePasse(null);
		return mappers.fromClient(savedClient);
	}

	@Override
	public ClientDTO update(String id, ClientDTO clientDTO) {
		log.info("IN update()");
		try {
			Client client = clientRepository.findById(id).orElse(null);
			if(client!=null) {
				String password = client.getMotDePasse();
				String email = client.getEmail();
				clientDTO.setMotdePasse(password);
				clientDTO.setEmail(email);
				clientDTO.setId(id);
				Client c = mappers.fromClientDTO(clientDTO);
				Client updated = clientRepository.save(c);
				log.info(CUSTOMER_UPDATED);
				return mappers.fromClient(updated);
			}
			else {
				log.warn(CUSTOMER_NOT_UPDATED);
				return null;
			}
		}catch(Exception e) {
			log.error(CUSTOMER_NOT_UPDATED);
			return null;
		}
	}

	@Override
	public void deleteById(String id) {
		log.info("IN deleteById()");
		try {
			clientRepository.deleteById(id);
			log.info(CUSTOMER_DELETED);
		}catch(Exception e) {
			log.error(CUSTOMER_NOT_DELETED);
		}
		
	}

	@Override
	public void deleteByCin(String cin) {
		log.info("IN deleteByCin()");
		try {
			ClientDTO client = findByCin(cin);
			if(client!=null) {
				clientRepository.deleteById(client.getId());
				log.info(CUSTOMER_DELETED);
			}
		}catch(Exception e) {
			log.error(CUSTOMER_NOT_DELETED);
		}
		
	}

	@Override
	public void deleteAll() {
		log.info("IN deleteAll()");
		try {
			clientRepository.deleteAll();
			log.info(CUSTOMER_DELETED);
		}catch(Exception e) {
			log.error(CUSTOMER_NOT_DELETED);
		}
		
	}

	@Override
	public List<ClientDTO> findByName(String name) {
		log.info("IN findByName()");
		try {
			List<Client> clients = clientRepository.findByNameContains(name);
			log.info(CUSTOMER_FOUND);
			return mappers.fromListOfClients(clients);
		}catch(Exception e) {
			log.error(CUSTOMER_NOT_FOUND);
			return Collections.emptyList();
		}
		
	}

	@Override
	public void updatePassword(String id, String oldPassword, String newPassword) {
		log.info("IN updatePassword()");
		try {
			Client client = clientRepository.findById(id).orElse(null);
			if(client!=null && oldPassword.equals(client.getMotDePasse())) {
				client.setMotDePasse(newPassword);
				clientRepository.save(client);
				log.info(CUSTOMER_UPDATED);
			}
			else {
				log.warn(CUSTOMER_NOT_UPDATED);
			}
			
		}catch(Exception e) {
			log.error(CUSTOMER_NOT_UPDATED);
		}
		
	}


}
