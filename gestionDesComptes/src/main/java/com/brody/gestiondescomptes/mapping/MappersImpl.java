package com.brody.gestiondescomptes.mapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.brody.gestiondescomptes.dto.CompteDTO;
import com.brody.gestiondescomptes.entities.Compte;


@Service
public class MappersImpl implements Mappers{

	@Override
	public CompteDTO fromCompte(Compte compte) {
		try {
			return new CompteDTO(
					compte.getRib(), compte.getSolde(), compte.getDevise(), compte.getStatus(), compte.getCreatedAt(), compte.getClientId()
					);
		}catch(Exception e) {
			return null;
		}
	
	}

	@Override
	public Compte fromCompteDTO(CompteDTO compteDTO) {
		try {
			return new Compte(
					compteDTO.getRib(), compteDTO.getSolde(), compteDTO.getDevise(), compteDTO.getStatus(), compteDTO.getCreatedAt(), compteDTO.getClientId()
					);
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public List<CompteDTO> fromListOfComptes(List<Compte> comptes) {
		try {
			return comptes.stream()
					.map(this::fromCompte)
					.collect(Collectors.toList());
		}catch(Exception e) {
			return Collections.emptyList();
		}
	}

}
