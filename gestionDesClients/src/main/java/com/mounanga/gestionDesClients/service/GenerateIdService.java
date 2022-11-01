package com.mounanga.gestionDesClients.service;

import com.mounanga.gestionDesClients.enums.Sexe;

public interface GenerateIdService {
	String generateClientId(Sexe sexe);
}
