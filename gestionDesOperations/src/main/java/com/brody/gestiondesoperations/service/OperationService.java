package com.brody.gestiondesoperations.service;

import java.util.List;

import com.brody.gestiondesoperations.dto.HistoriqueDTO;
import com.brody.gestiondesoperations.dto.OperationDTO;
import com.brody.gestiondesoperations.dto.TransferDTO;
import com.brody.gestiondesoperations.exception.BalanceNotSufficientException;
import com.brody.gestiondesoperations.exception.BankAccountNotFoundException;
import com.brody.gestiondesoperations.exception.CompteNotActivatedException;
import com.brody.gestiondesoperations.exception.CompteSuspendueException;

public interface OperationService {
	
	void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException, CompteSuspendueException, CompteNotActivatedException;
	
	void credit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException, CompteSuspendueException, CompteNotActivatedException;
	
	
	List<OperationDTO> historique(String accountId);
	
	HistoriqueDTO getHistorique(String accountId, int page, int size) throws BankAccountNotFoundException;
	
	void transfert(TransferDTO transferDTO) throws BankAccountNotFoundException, BalanceNotSufficientException, CompteSuspendueException, CompteNotActivatedException;
	
}
