package com.mounanga.gestionDesClients;

import java.util.UUID;

import org.junit.runner.RunWith;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mounanga.gestionDesClients.dto.ClientDTO;
import com.mounanga.gestionDesClients.enums.Sexe;
import com.mounanga.gestionDesClients.exception.ClientNotFoundException;

import com.mounanga.gestionDesClients.mapping.Mappers;
import com.mounanga.gestionDesClients.repository.ClientRepository;
import com.mounanga.gestionDesClients.service.ClientServiceImpl;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ClientServiceTest {
	
	@Autowired
	ClientServiceImpl clientServiceImpl;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	Mappers mappers;
	
	@Test
	public void testFindById() {
		
		String id = UUID.randomUUID().toString();
		ClientDTO clientDTO = new ClientDTO(id, "Prenom", "Nom", "Africa", "Africa", UUID.randomUUID().toString(), Sexe.M);
		clientRepository.save(mappers.fromClientDTO(clientDTO));
		ClientDTO c;
		try {
			c = clientServiceImpl.findById(id);
			assertNotNull(c);
		} catch (ClientNotFoundException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
}
