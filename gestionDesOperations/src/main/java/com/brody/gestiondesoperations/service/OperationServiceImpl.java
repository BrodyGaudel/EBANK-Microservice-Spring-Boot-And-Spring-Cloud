package com.brody.gestiondesoperations.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.brody.gestiondesoperations.dto.CompteDTO;
import com.brody.gestiondesoperations.dto.ConvertFromResponse;
import com.brody.gestiondesoperations.dto.HistoriqueDTO;
import com.brody.gestiondesoperations.dto.OperationDTO;
import com.brody.gestiondesoperations.dto.Rate;
import com.brody.gestiondesoperations.dto.TransferDTO;
import com.brody.gestiondesoperations.entities.Operation;
import com.brody.gestiondesoperations.enums.Type;
import com.brody.gestiondesoperations.exception.BalanceNotSufficientException;
import com.brody.gestiondesoperations.exception.BankAccountNotFoundException;
import com.brody.gestiondesoperations.exception.CompteNotActivatedException;
import com.brody.gestiondesoperations.exception.CompteSuspendueException;
import com.brody.gestiondesoperations.feign.CompteRestClient;
import com.brody.gestiondesoperations.feign.XeRestClient;
import com.brody.gestiondesoperations.mapping.Mappers;
import com.brody.gestiondesoperations.repository.OperationRepository;


@Service
public class OperationServiceImpl implements OperationService {
	
	private static final Logger log = Logger.getLogger(OperationServiceImpl.class);
	
	
	private OperationRepository operationRepository;
	private CompteRestClient compteRestClient;
	private Mappers mappers;
	private XeRestClient xe;
	
	

	public OperationServiceImpl(OperationRepository operationRepository,CompteRestClient compteRestClient, Mappers mappers, XeRestClient xe) {
		
		this.operationRepository = operationRepository;
		this.compteRestClient = compteRestClient;
		this.mappers = mappers;
		this.xe = xe;
	}

	@Override
	public void debit(String accountId, double amount, String description)
			throws BankAccountNotFoundException, BalanceNotSufficientException, CompteSuspendueException, CompteNotActivatedException {
		log.info("IN debit() :");
		CompteDTO compteDTO = compteRestClient.findByRib(accountId);
		String status = compteDTO.getStatus().toString();
		if(status.equals("SUSPENDED")) {
			String message = "COMPTE "+compteDTO.getRib()+" SUSPENDU";
			throw new CompteSuspendueException(message);
		}
		
		if(status.equals("CREATED")) {
			String message = "COMPTE "+compteDTO.getRib()+" NON ACTIVEE";
			throw new CompteNotActivatedException(message);
		}
		
		if(compteDTO.getSolde()<amount) {
			throw new BalanceNotSufficientException("SOLDE INSUFFISANT");
		}
		
		if(status.equals("ACTIVATED")) {
			Operation operation = new Operation();
			operation.setId(UUID.randomUUID().toString());
			operation.setType(Type.DEBIT);
			operation.setAmount(amount);
			operation.setOperationDate(new Date());
			operation.setDescription(description);
			operation.setCompteId(compteDTO.getRib());
			operationRepository.save(operation);
			log.info("DEBIT SAVED");
			compteDTO.setSolde(compteDTO.getSolde()-amount);
			compteRestClient.update(compteDTO.getRib(), compteDTO);
		}
		
			
		
	}

	@Override
	public void credit(String accountId, double amount, String description)
			throws BankAccountNotFoundException, BalanceNotSufficientException, CompteSuspendueException, CompteNotActivatedException {
		log.info("IN Credit() :");
		CompteDTO compteDTO = compteRestClient.findByRib(accountId);
		
		String status = compteDTO.getStatus().toString();
		
		if(status.equals("SUSPENDED")) {
			String message = "COMPTE "+compteDTO.getRib()+" SUSPENDU";
			throw new CompteSuspendueException(message);
		}
		
		if(status.equals("CREATED")) {
			String message = "COMPTE "+compteDTO.getRib()+" NON ACTIVEE";
			throw new CompteNotActivatedException(message);
		}
		
		if(amount<0) {
			throw new BalanceNotSufficientException("MONTANT INSUFFISANT");
		}
		
		if(status.equals("ACTIVATED")) {
			Operation operation = new Operation();
			
			operation.setId(UUID.randomUUID().toString());
			operation.setType(Type.CREDIT);
			operation.setAmount(amount);
			operation.setOperationDate(new Date());
			operation.setDescription(description);
			operation.setCompteId(compteDTO.getRib());
			operationRepository.save(operation);
			log.info("CREDIT SAVED");
			compteDTO.setSolde(compteDTO.getSolde()+amount);
			compteRestClient.update(compteDTO.getRib(), compteDTO);
		}
		
		
		
		
	}


	@Override
	public List<OperationDTO> historique(String accountId) {
		log.info("IN historique() :");
		List<Operation> operations = operationRepository.findByCompteId(accountId);
		log.info("HISTORIQUE FOUND");
		return operations.stream()
				.map(op -> mappers.fromOperation(op))
				.collect(Collectors.toList());
	}

	@Override
	public HistoriqueDTO getHistorique(String accountId, int page, int size) throws BankAccountNotFoundException {
		log.info("IN getHistorique() :");
		CompteDTO bankAccount = compteRestClient.findByRib(accountId);
		if(bankAccount==null) {
			throw new BankAccountNotFoundException("Bank Account Not Found");
		}
		
		Page<Operation> operations = operationRepository
				.findByCompteIdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
		HistoriqueDTO accountHistoryDTO = new HistoriqueDTO();
		List<OperationDTO> accountOperationDTOS = operations.getContent()
				.stream()
				.map(op -> mappers.fromOperation(op))
				.collect(Collectors.toList());
		
		
		accountHistoryDTO.setOperationDTOS(accountOperationDTOS);
		accountHistoryDTO.setAccountId(bankAccount.getRib());
		accountHistoryDTO.setBalance(bankAccount.getSolde());
		accountHistoryDTO.setPageSize(size);
		accountHistoryDTO.setCurrentPage(page);
		accountHistoryDTO.setTotalPages(operations.getTotalPages());
		log.info("HISTORIQUE PAGEABLE FOUND");
		return accountHistoryDTO;
	}

	@Override
	public void transfert(TransferDTO transferDTO) throws BankAccountNotFoundException, BalanceNotSufficientException, CompteSuspendueException, CompteNotActivatedException {
		
		String source = transferDTO.getAccountSource();
		String destination = transferDTO.getAccountDestination();
		double amount = transferDTO.getAmount();
		
		double frais = 0.05;
		CompteDTO compteSource = compteRestClient.findByRib(source);
		CompteDTO compteDestination = compteRestClient.findByRib(destination);
		if(compteSource==null) {
			throw new BankAccountNotFoundException("COMPTE SOURCE NOT FOUND");
		}
		if(compteDestination==null) {
			throw new BankAccountNotFoundException("COMPTE DESTINATION NOT FOUND");
		}
		
		String deviseSource = compteSource.getDevise().toString();
		String deviseDestination = compteDestination.getDevise().toString();
		
		if(deviseSource.equals(deviseDestination)) {
			frais = 0.02;
		}
		
		ConvertFromResponse convertFromResponse = xe.convertion(deviseSource, deviseDestination, amount);
		
		double montant = 0;
		if(convertFromResponse!=null) {
			
			List<Rate> rates = convertFromResponse.getTo();
			Rate rate = rates.get(0);
			montant = rate.getMid();
			double k = (montant*frais);
			double debiting = amount + (montant*frais);
			String message1 = "TRANSFERT DE :"+compteSource.getRib();
			String message2 = "VER :"+compteDestination.getRib();
			String message3 = "MONTANT ENVOYEE :"+amount+" DEVISE :"+deviseSource;
			String message4 = "MONTANT RECU :"+montant+" DEVISE :"+deviseDestination;
			String message5 = "FRAIS :"+k+" SOIT :"+frais;
			String description = message1+"|"+message2+"|"+message3+"|"+message4+"|"+message5;
			debit(source, debiting, description);
			credit(destination, montant, description);
		}	
		
	}


	

}
