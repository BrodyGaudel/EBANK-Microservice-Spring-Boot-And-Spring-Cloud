package com.brody.gestiondesoperations.restcontroller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.brody.gestiondesoperations.dto.CreditDTO;
import com.brody.gestiondesoperations.dto.DebitDTO;
import com.brody.gestiondesoperations.dto.HistoriqueDTO;
import com.brody.gestiondesoperations.dto.OperationDTO;
import com.brody.gestiondesoperations.dto.TransferDTO;
import com.brody.gestiondesoperations.exception.BalanceNotSufficientException;
import com.brody.gestiondesoperations.exception.BankAccountNotFoundException;
import com.brody.gestiondesoperations.exception.CompteNotActivatedException;
import com.brody.gestiondesoperations.exception.CompteSuspendueException;
import com.brody.gestiondesoperations.service.OperationService;


@RestController
@RequestMapping("/operation")
@CrossOrigin(origins = "*")
public class OperationRestController {
	
	
	private OperationService operationService;
	
	
	public OperationRestController(OperationService operationService) {
	
		this.operationService = operationService;
	}

	@PostMapping("/debit")
	@ResponseBody
	public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException, CompteSuspendueException, CompteNotActivatedException {
		operationService.debit(debitDTO.getAccountId(), debitDTO.getAmount(), debitDTO.getDescription());
		return debitDTO;
	}
	
	@PostMapping("/credit")
	@ResponseBody
	public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException, BalanceNotSufficientException, CompteSuspendueException, CompteNotActivatedException{
		operationService.credit(creditDTO.getAccountId(), creditDTO.getAmount(), creditDTO.getDescription());
		return creditDTO;
	}
	
	@GetMapping("/historique/{accountId}")
	@ResponseBody
	public List<OperationDTO> historique(@PathVariable(name="accountId") String accountId){
		return operationService.historique(accountId);
	}
	
	@GetMapping("/{accountId}/pageOperations")
	@ResponseBody
	public HistoriqueDTO getHistorique(@PathVariable String accountId, @RequestParam(name ="page", defaultValue = "0") int page, @RequestParam(name ="size", defaultValue = "5") int size) throws BankAccountNotFoundException{
		return operationService.getHistorique(accountId, page, size);
	}
	
	@PostMapping("/transfer")
	@ResponseBody
	public TransferDTO transfert(@RequestBody TransferDTO transferDTO) throws BankAccountNotFoundException, BalanceNotSufficientException, CompteSuspendueException, CompteNotActivatedException {
		operationService.transfert(transferDTO);
		return transferDTO;
	}
	
	
	

	
}
