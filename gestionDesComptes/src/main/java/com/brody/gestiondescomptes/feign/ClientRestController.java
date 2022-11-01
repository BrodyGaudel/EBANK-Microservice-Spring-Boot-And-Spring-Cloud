package com.brody.gestiondescomptes.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brody.gestiondescomptes.dto.ClientDTO;

@FeignClient(name = "CLIENT-SERVICE")
public interface ClientRestController {
	
	@GetMapping(path="client/findById/{id}")
	@ResponseBody
	ClientDTO getClientById(@PathVariable(name = "id") String id);


}
