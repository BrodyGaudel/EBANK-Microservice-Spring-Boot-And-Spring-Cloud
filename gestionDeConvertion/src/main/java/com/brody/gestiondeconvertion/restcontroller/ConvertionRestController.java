package com.brody.gestiondeconvertion.restcontroller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.brody.gestiondeconvertion.exception.XecdApiException;
import com.brody.gestiondeconvertion.model.ConvertFromResponse;
import com.brody.gestiondeconvertion.service.XecdApiService;

@RestController
@RequestMapping("/convertion")
@CrossOrigin(origins = "*")
public class ConvertionRestController {
	
	private static final String TND = "TND";
	private static final String XAF = "XAF";
	private XecdApiService xecdApiService;

	public ConvertionRestController(XecdApiService xecdApiService) {
		
		this.xecdApiService = xecdApiService;
	}
	
	@GetMapping("/convert")
	@ResponseBody
	public ConvertFromResponse convertion() throws XecdApiException {
		String accountId = "xe account id ";
		String apiKey = "xe api key";
		
		return xecdApiService.convertFrom(
				accountId, apiKey, TND, XAF, (double) 5000, false);
	}
	
	@GetMapping("/convertir/{from}/{to}/{amount}")
	@ResponseBody
	public ConvertFromResponse convertion(@PathVariable(name="from") String from, @PathVariable(name="to") String to, @PathVariable(name="amount") double amount) throws XecdApiException {
		String accountId = "xe account id ";
		String apiKey = "xe api key";
		
		return xecdApiService.convertFrom(
				accountId, apiKey, from, to, amount, false);
	}
	

}
