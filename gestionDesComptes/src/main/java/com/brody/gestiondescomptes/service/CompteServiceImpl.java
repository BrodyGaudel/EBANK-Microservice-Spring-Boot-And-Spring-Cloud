package com.brody.gestiondescomptes.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.brody.gestiondescomptes.dto.ClientDTO;
import com.brody.gestiondescomptes.dto.CompteDTO;
import com.brody.gestiondescomptes.dto.ConvertFromResponse;
import com.brody.gestiondescomptes.dto.Rate;
import com.brody.gestiondescomptes.entities.Compte;
import com.brody.gestiondescomptes.enums.AccountStatus;
import com.brody.gestiondescomptes.enums.Devise;
import com.brody.gestiondescomptes.exception.ClientAlreadyHaveAccountException;
import com.brody.gestiondescomptes.exception.ClientNotExistException;
import com.brody.gestiondescomptes.exception.CompteNotFoundException;
import com.brody.gestiondescomptes.feign.ClientRestController;
import com.brody.gestiondescomptes.feign.XeRestClient;
import com.brody.gestiondescomptes.mapping.Mappers;
import com.brody.gestiondescomptes.repository.CompteRepository;


@Service
@Transactional
public class CompteServiceImpl implements CompteService {
	
	private static final Logger log = Logger.getLogger(CompteServiceImpl.class);
	
	private static final String CLIENT_HAVE_ACCOUNT ="CLIENT HAVE ACCOUNT";

	private static final String CLIENT_NOT_EXIST = "CLIENT DOES NOT EXIST";

	private static final String COMPTE_NOT_FOUND = "COMPTE NOT FOUND";
	
	private CompteRepository compteRepository;
	private GenerateIdService generateIdService;
	private ClientRestController clientRestController;
	private Mappers mappers;
	private XeRestClient xe;
	
	
	public CompteServiceImpl(CompteRepository compteRepository, GenerateIdService generateIdService, ClientRestController clientRestController, Mappers mappers, XeRestClient xe) {
	
		this.compteRepository = compteRepository;
		this.generateIdService = generateIdService;
		this.clientRestController = clientRestController;
		this.mappers = mappers;
		this.xe = xe;
	}

	@Override
	public CompteDTO save(CompteDTO compteDTO) throws ClientAlreadyHaveAccountException, ClientNotExistException {
		log.info("In save()");
		Compte compte = mappers.fromCompteDTO(compteDTO);
		ClientDTO clientDTO = new ClientDTO();
		
		boolean existeDeja = false;
		try {
			clientDTO = clientRestController.getClientById(compteDTO.getClientId());
		}catch(Exception e) {
			clientDTO = null;
		}
		
		
		if(clientDTO!=null) {
			List<Compte> comptes = compteRepository.findAll();
			
			for(Compte c: comptes) {
				if(c.getClientId().equals(compteDTO.getClientId())) {
					existeDeja = true;
				}
			}
			
			if(existeDeja) {
				throw new ClientAlreadyHaveAccountException(CLIENT_HAVE_ACCOUNT);
			}
			else {
				compte.setRib(generateIdService.generateClientId());
				Compte savedCompte = compteRepository.save(compte);
				log.info("COMPTE SAVED");
				return mappers.fromCompte(savedCompte);
			}
		}
		else {
			throw new ClientNotExistException(CLIENT_NOT_EXIST);
		}
	}

	@Override
	public CompteDTO update(String rib, CompteDTO compteDTO) {
		log.info("In update()");
		try {
			
			Compte compte = mappers.fromCompteDTO(compteDTO);
			compte.setRib(rib);
			Compte compteUpdated = compteRepository.save(compte);
			log.info("COMPTE UPDATED");
			return mappers.fromCompte(compteUpdated);
		}catch(Exception e) {
			log.error("COMPTE NOT UPDATED");
			return null;
		}
	}

	@Override
	public CompteDTO findByRib(String rib) throws CompteNotFoundException {
		log.info("In findByRib()");
		Compte compte = compteRepository.findById(rib)
				.orElseThrow( () -> new CompteNotFoundException(COMPTE_NOT_FOUND));
		log.info("COMPTE FOUND");
		return mappers.fromCompte(compte);
	}

	@Override
	public CompteDTO findByClientId(String clientId) {
		log.info("In findByClientId()");
		try {
			List<Compte> comptes = compteRepository.findAll();
			List<Compte> listOfComptes = comptes.stream()
					.filter(c -> c.getClientId().equals(clientId))
					.collect(Collectors.toList());
			Compte compte = listOfComptes.get(0);
			log.info("COMPTE FOUND");
			return mappers.fromCompte(compte);
		}catch(Exception e) {
			log.error("COMPTE NOT FOUND");
			return null;
		}
	}

	@Override
	public List<CompteDTO> findAll() {
		log.info("In findAll()");
		try {
			List<Compte> comptes = compteRepository.findAll();
			log.info("COMPTE FOUDS");
			return mappers.fromListOfComptes(comptes);
		}catch(Exception e) {
			log.error("COMPTES NOT FOUND");
			return Collections.emptyList();
		}
	}

	@Override
	public void deleteByRib(String rib) {
		log.info("In deleteByRib()");
		try {
			compteRepository.deleteById(rib);
		}catch(Exception e) {
			log.error("COMPTE NOT DELETED");
		}
		
	}

	@Override
	public void deleteByClientId(String clientId) {
		log.info("In deleteByClientId()");
		try {
			List<Compte> comptes = compteRepository.findAll();
			List<Compte> comptesToDelete = comptes.stream().
					filter(c -> c.getClientId().equals(clientId))
					.collect(Collectors.toList());
			for(Compte c: comptesToDelete) {
				compteRepository.deleteById(c.getRib());
			}
		}catch(Exception e) {
			log.error("COMPTE NOT DELETED");
		}
		
	}

	@Override
	public void deleteAll() {
		log.info("In deleteAll()");
		try {
			compteRepository.deleteAll();
			log.info("COMPTES DELETED");
		}catch(Exception e) {
			log.error("COMPTES NOT DELETED");
		}
		
	}

	@Override
	public CompteDTO activate(String rib) {
		
		log.info("In activate()");
		try {
			Compte compte = compteRepository.findById(rib).orElse(null);
			if(compte!=null) {
				compte.setStatus(AccountStatus.ACTIVATED);
				Compte compteUpdated = compteRepository.save(compte);
				log.info("COMPTES ACTIVATED");
				return mappers.fromCompte(compteUpdated);
			}
			else {
				log.warn("COMPTES NOT ACTIVATED : COMPTE NOT FOUND OR IT WAS NULL");
				return null;
			}
			
		}catch(Exception e) {
			log.error("COMPTES NOT ACTIVATED : "+e);
			return null;
		}
	}

	@Override
	public CompteDTO suspend(String rib) {
		log.info("In suspend()");
		try {
			Compte compte = compteRepository.findById(rib).orElse(null);
			if(compte!=null) {
				compte.setStatus(AccountStatus.SUSPENDED);
				Compte compteUpdated = compteRepository.save(compte);
				log.info("COMPTES SUSPENDED");
				return mappers.fromCompte(compteUpdated);
			}else {
				log.warn("COMPTES NOT SUSPENDED : COMPTE NOT FOUND OR IT WAS NULL");
				return null;
			}
			
		}catch(Exception e) {
			log.info("COMPTES NOT SUSPENDED : "+e);
			return null;
		}
	}

	@Override
	public CompteDTO desactivate(String rib) {
		log.info("In desactivate()");
		try {
			Compte compte = compteRepository.findById(rib).orElse(null);
			if(compte!=null) {
				compte.setStatus(AccountStatus.CREATED);
				Compte compteUpdated = compteRepository.save(compte);
				log.info("COMPTES DESACTIVATED");
				return mappers.fromCompte(compteUpdated);
			}else {
				log.warn("COMPTES NOT DESACTIVATED : COMPTE NOT FOUND OR IT WAS NULL");
				return null;
			}
			
		}catch(Exception e) {
			log.info("COMPTES NOT DESACTIVATED : "+e);
			return null;
		}
	}

	@Override
	public CompteDTO create(CompteDTO compteDTO) throws ClientAlreadyHaveAccountException, ClientNotExistException {
		
		log.info("In save()");
		Compte compte = mappers.fromCompteDTO(compteDTO);
		ClientDTO clientDTO = new ClientDTO();
		
		boolean existeDeja = false;
		try {
			clientDTO = clientRestController.getClientById(compteDTO.getClientId());
		}catch(Exception e) {
			clientDTO = null;
		}
		
		
		if(clientDTO!=null) {
			List<Compte> comptes = compteRepository.findAll();
			
			for(Compte c: comptes) {
				if(c.getClientId().equals(compteDTO.getClientId())) {
					existeDeja = true;
				}
			}
			
			if(existeDeja) {
				throw new ClientAlreadyHaveAccountException(CLIENT_HAVE_ACCOUNT);
			}
			else {
				compte.setStatus(AccountStatus.CREATED);
				compte.setSolde(0);
				compte.setRib(generateIdService.generateClientId());
				Compte savedCompte = compteRepository.save(compte);
				log.info("COMPTE CREATED");
				return mappers.fromCompte(savedCompte);
			}
		}
		else {
			throw new ClientNotExistException(CLIENT_NOT_EXIST);
		}
	}

	@Override
	public CompteDTO updateDevise(String rib, String newDevise) {
		
		log.info("In updateDevise()");
		try {
			Compte compte = compteRepository.findById(rib).orElse(null);
			if(compte!=null) {
				String lastDevise = compte.getDevise().toString();
				
				log.info("DEVISE UPDATED");
				ConvertFromResponse convert = xe.convertion(lastDevise, newDevise, compte.getSolde());
				List<Rate> rates = convert.getTo();
				Rate rate = rates.get(0);
				double amount = rate.getMid();
				compte.setDevise(Devise.valueOf(newDevise));
				compte.setSolde(amount);
				Compte compteUpdated = compteRepository.save(compte);
				log.info("DEVISE UPDATED");
				return mappers.fromCompte(compteUpdated);
			}else {
				log.warn("DEVISE NOT UPDATED : COMPTE NOT FOUND OR IT WAS NULL");
				return null;
			}
			
		}catch(Exception e) {
			log.info("DEVISE NOT UPDATED : "+e);
			return null;
		}
	}

}
