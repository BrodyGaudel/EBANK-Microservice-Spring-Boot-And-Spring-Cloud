package com.brody.gestiondescomptes.mapping;

import java.util.List;

import com.brody.gestiondescomptes.dto.CompteDTO;
import com.brody.gestiondescomptes.entities.Compte;

public interface Mappers {
	CompteDTO fromCompte(Compte compte);
	Compte fromCompteDTO(CompteDTO compteDTO);
	List<CompteDTO> fromListOfComptes(List<Compte> comptes);
}
