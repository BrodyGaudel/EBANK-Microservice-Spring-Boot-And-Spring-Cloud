package com.mounanga.gestionDesClients.mapping;

import java.util.List;

import com.mounanga.gestionDesClients.dto.ClientDTO;
import com.mounanga.gestionDesClients.entities.Client;

public interface Mappers {
	
	Client fromClientDTO(ClientDTO clientDTO);
	ClientDTO fromClient(Client client);
	
	List<ClientDTO> fromListOfClients(List<Client> clients);
	
	Client copyClient(Client client);

}
