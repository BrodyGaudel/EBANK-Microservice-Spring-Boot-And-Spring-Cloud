package com.brody.gestiondescomptes.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brody.gestiondescomptes.dto.ConvertFromResponse;

@FeignClient(name = "COVERTISSEUR-SERVICE")
public interface XeRestClient {
	
	@GetMapping(path="convertion/convertir/{from}/{to}/{amount}")
	@ResponseBody
	ConvertFromResponse convertion(@PathVariable(name="from") String from, @PathVariable(name="to") String to, @PathVariable(name="amount") double amount);


}
