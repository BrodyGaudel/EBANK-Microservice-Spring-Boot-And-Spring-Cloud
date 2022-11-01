package com.mounanga.gestionDesClients.mapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mounanga.gestionDesClients.dto.ClientDTO;
import com.mounanga.gestionDesClients.entities.Client;

@Service
public class MappersImpl implements Mappers {

	@Override
	public Client fromClientDTO(ClientDTO clientDTO) {
		try {
			Client client = new Client();
			client.setId(clientDTO.getId());
			client.setPrenom(clientDTO.getPrenom());
			client.setNom(clientDTO.getNom());
			client.setNationalite(clientDTO.getNationalite());
			client.setLieuxDeNaissance(clientDTO.getLieuxDeNaissance());
			client.setDateDeNaissance(clientDTO.getDateDeNaissance());
			client.setCin(clientDTO.getCin());
			client.setSexe(clientDTO.getSexe());
			client.setEmail(clientDTO.getEmail());
			client.setPhone(clientDTO.getPhone());
			client.setMotDePasse(clientDTO.getMotdePasse());
			return client;
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public ClientDTO fromClient(Client client) {
		try {
			ClientDTO clientDTO = new ClientDTO();
			clientDTO.setId(client.getId());
			clientDTO.setPrenom(client.getPrenom());
			clientDTO.setNom(client.getNom());
			clientDTO.setNationalite(client.getNationalite());
			clientDTO.setLieuxDeNaissance(client.getLieuxDeNaissance());
			clientDTO.setDateDeNaissance(client.getDateDeNaissance());
			clientDTO.setCin(client.getCin());
			clientDTO.setSexe(client.getSexe());
			clientDTO.setEmail(client.getEmail());
			clientDTO.setPhone(client.getPhone());
			clientDTO.setMotdePasse(null);
			return clientDTO;
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public List<ClientDTO> fromListOfClients(List<Client> clients) {
		try {
			return clients.stream()
					.map(this::fromClient)
					.collect(Collectors.toList());
		}catch(Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public Client copyClient(Client client) {
		Client c = new Client();
		c.setId(client.getId());
		c.setPrenom(client.getPrenom());
		c.setNom(client.getNom());
		c.setNationalite(client.getNationalite());
		c.setLieuxDeNaissance(client.getLieuxDeNaissance());
		c.setDateDeNaissance(client.getDateDeNaissance());
		c.setCin(client.getCin());
		c.setSexe(client.getSexe());
		c.setEmail(client.getEmail());
		c.setPhone(client.getPhone());
		c.setMotDePasse(client.getMotDePasse());
		return c;
	}

}
