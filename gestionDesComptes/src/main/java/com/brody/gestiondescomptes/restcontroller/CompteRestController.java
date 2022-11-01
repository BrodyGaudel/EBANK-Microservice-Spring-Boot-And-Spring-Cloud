package com.brody.gestiondescomptes.restcontroller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.brody.gestiondescomptes.dto.CompteDTO;
import com.brody.gestiondescomptes.exception.ClientAlreadyHaveAccountException;
import com.brody.gestiondescomptes.exception.ClientNotExistException;
import com.brody.gestiondescomptes.exception.CompteNotFoundException;
import com.brody.gestiondescomptes.service.CompteService;

@RestController
@RequestMapping("/compte")
@CrossOrigin(origins = "*")
public class CompteRestController {
	
	private CompteService compteService;

	public CompteRestController(CompteService compteService) {
		
		this.compteService = compteService;
	}
	
	/**
	 * FOR ADMINISTRATOR ONLY
	 * @param compteDTO
	 * @return
	 * @throws ClientAlreadyHaveAccountException
	 * @throws ClientNotExistException
	 */
	@PostMapping("/save")
	@ResponseBody
	public CompteDTO save(@RequestBody CompteDTO compteDTO) throws ClientAlreadyHaveAccountException, ClientNotExistException{
		return compteService.save(compteDTO);
	}
	
	/**
	 * FOR USERS
	 * @param compteDTO
	 * @return
	 * @throws ClientAlreadyHaveAccountException
	 * @throws ClientNotExistException
	 */
	@PostMapping("/create")
	@ResponseBody
	public CompteDTO create(@RequestBody CompteDTO compteDTO) throws ClientAlreadyHaveAccountException, ClientNotExistException{
		return compteService.create(compteDTO);
	}
	
	@PutMapping("/update/{rib}")
	@ResponseBody
	public CompteDTO update(@PathVariable(name ="rib") String rib, @RequestBody CompteDTO compteDTO) {
		return compteService.update(rib, compteDTO);
	}
	
	@GetMapping("/findByRib/{rib}")
	@ResponseBody
	public CompteDTO findByRib(@PathVariable(name ="rib") String rib) throws CompteNotFoundException{
		return compteService.findByRib(rib);
	}
	
	@GetMapping("/findByClientId/{clientId}")
	@ResponseBody
	public CompteDTO findByClientId(@PathVariable(name ="clientId") String clientId) {
		return compteService.findByClientId(clientId);
	}
	
	@GetMapping("/findAll")
	@ResponseBody
	public List<CompteDTO> findAll(){
		return compteService.findAll();
				
	}
	
	@DeleteMapping("/deleteByRib/{rib}")
	public void deleteByRib(@PathVariable(name ="rib") String rib) {
		compteService.deleteByRib(rib);
	}
	
	@DeleteMapping("/deleteByClientId/{clientId}")
	public void deleteByClientId(@PathVariable(name ="clientId") String clientId) {
		compteService.deleteByClientId(clientId);
	}
	
	@DeleteMapping("/deleteAll")
	public void deleteAll() {
		compteService.deleteAll();
	}
	
	@PutMapping("/activate/{rib}")
	@ResponseBody
	public CompteDTO activate(@PathVariable(name ="rib") String rib) {
		return compteService.activate(rib);
	}
	
	@PutMapping("/suspend/{rib}")
	@ResponseBody
	public CompteDTO suspend(@PathVariable(name ="rib") String rib) {
		return compteService.suspend(rib);
	}
	
	@PutMapping("/desactivate/{rib}")
	@ResponseBody
	public CompteDTO desactivate(@PathVariable(name ="rib") String rib) {
		return compteService.desactivate(rib);
	}
	

	@PutMapping("/devise/{rib}/{newDevise}")
	@ResponseBody
	public CompteDTO updateDevise(@PathVariable(name ="rib") String rib, @PathVariable(name ="newDevise") String newDevise) {
		return compteService.updateDevise(rib, newDevise);
	}

}
