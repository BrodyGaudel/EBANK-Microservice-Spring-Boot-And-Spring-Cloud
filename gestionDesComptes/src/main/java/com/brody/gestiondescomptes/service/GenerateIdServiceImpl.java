package com.brody.gestiondescomptes.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.brody.gestiondescomptes.entities.CompterCompte;
import com.brody.gestiondescomptes.repository.CompterCompteRepository;


@Service
@Transactional
public class GenerateIdServiceImpl implements GenerateIdService {
	
	private static final Logger log = Logger.getLogger(GenerateIdServiceImpl.class);
	private static final String ID_GENERATED = "Id Generated";
	
	private CompterCompteRepository compterCompteRepository;
	
	public GenerateIdServiceImpl(CompterCompteRepository compterCompteRepository) {
		
		this.compterCompteRepository = compterCompteRepository;
	}



	@Override
	public String generateClientId() {
		log.info("In generateProductId()");
		try {
			List<CompterCompte> compters = compterCompteRepository.findAll();
			if(compters.isEmpty()) {
				CompterCompte compter = new CompterCompte((long) 99999);
				log.info(ID_GENERATED);
				return generateForClient(compter);
			}
			else {
				CompterCompte compter = compters.get(compters.size()-1);
				compterCompteRepository.deleteById(compter.getId());
				log.info(ID_GENERATED); 
				return generateForClient(compter);
			}	
		}catch(Exception e) {
			log.info("In Not Generated: Exception :"+e);
			return null;
		}
	}
	
	private String generateForClient(CompterCompte compter) {
		Long cpt = compter.getId()+1;
		CompterCompte compt = new CompterCompte(cpt);
		compterCompteRepository.save(compt);
		String head = "2022";
		String body = cpt.toString();
		return head+body;
	}


}
