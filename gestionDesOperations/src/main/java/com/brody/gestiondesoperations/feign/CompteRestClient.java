package com.brody.gestiondesoperations.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brody.gestiondesoperations.dto.CompteDTO;



@FeignClient(name = "COMPTE-SERVICE")
public interface CompteRestClient {
	
	@GetMapping(path="compte/findByRib/{rib}")
	@ResponseBody
	CompteDTO findByRib(@PathVariable(name="rib") String rib);
	
	@PostMapping(path="compte/save")
	@ResponseBody
	CompteDTO save(@RequestBody CompteDTO compteDTO);
	
	@PutMapping(path="compte/update/{rib}")
	@ResponseBody
	CompteDTO update(@PathVariable(name="rib") String rib, @RequestBody CompteDTO compteDTO);

}
