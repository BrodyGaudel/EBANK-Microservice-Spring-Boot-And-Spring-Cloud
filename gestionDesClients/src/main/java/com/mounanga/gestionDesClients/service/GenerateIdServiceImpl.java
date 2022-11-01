package com.mounanga.gestionDesClients.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


import com.mounanga.gestionDesClients.entities.CompterClient;
import com.mounanga.gestionDesClients.enums.Sexe;
import com.mounanga.gestionDesClients.repository.CompterClientRepository;

@Service
public class GenerateIdServiceImpl implements GenerateIdService {
	
	private static final Logger log = Logger.getLogger(GenerateIdServiceImpl.class);
	private static final String ID_GENERATED = "Id Generated";
	
	private CompterClientRepository compterClientRepository;
	
	public GenerateIdServiceImpl(CompterClientRepository compterClientRepository) {
		
		this.compterClientRepository = compterClientRepository;
	}



	@Override
	public String generateClientId(Sexe sexe) {
		log.info("In generateProductId()");
		try {
			List<CompterClient> compters = compterClientRepository.findAll();
			if(compters.isEmpty()) {
				CompterClient compter = new CompterClient((long) 9999);
				log.info(ID_GENERATED);
				return generateForClient(compter, sexe);
			}
			else {
				CompterClient compter = compters.get(compters.size()-1);
				compterClientRepository.deleteById(compter.getId());
				log.info(ID_GENERATED); 
				return generateForClient(compter, sexe);
			}	
		}catch(Exception e) {
			log.info("In Not Generated: Exception :"+e);
			return null;
		}
	}
	
	private String generateForClient(CompterClient compter, Sexe sexe) {
		Long cpt = compter.getId()+1;
		CompterClient compt = new CompterClient(cpt);
		compterClientRepository.save(compt);
		String head = "2022";
		String afterHead = sexe.toString();
		String body = cpt.toString();
		return head+afterHead+body;
	}

}
