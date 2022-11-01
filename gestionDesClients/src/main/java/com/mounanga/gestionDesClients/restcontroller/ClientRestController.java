package com.mounanga.gestionDesClients.restcontroller;

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

import com.mounanga.gestionDesClients.dto.ClientDTO;
import com.mounanga.gestionDesClients.dto.FormUpdatePassword;
import com.mounanga.gestionDesClients.exception.CinAlreadyExistException;
import com.mounanga.gestionDesClients.exception.ClientNotFoundException;
import com.mounanga.gestionDesClients.exception.EmailAlreadyExistException;
import com.mounanga.gestionDesClients.exception.PhoneAlreadyExistException;
import com.mounanga.gestionDesClients.service.ClientService;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*")
public class ClientRestController {
	
	private ClientService clientService;

	public ClientRestController(ClientService clientService) {
		this.clientService = clientService;
	}
	
	@GetMapping("/findById/{id}")
	@ResponseBody
	ClientDTO findById(@PathVariable(name="id") String id) throws ClientNotFoundException{
		return clientService.findById(id);
	}
	
	@GetMapping("/findByCin/{cin}")
	@ResponseBody
	ClientDTO findByCin(@PathVariable(name="cin") String cin) {
		return clientService.findByCin(cin);
	}
	
	@GetMapping("/findByName/{name}")
	@ResponseBody
	List<ClientDTO> findByName(@PathVariable(name="name") String name){
		return clientService.findByName(name);
	}
	
	@GetMapping("/findAll")
	@ResponseBody
	List<ClientDTO> findAll(){
		return clientService.findAll();
	}
	
	@PostMapping("/save")
	@ResponseBody
	ClientDTO save(@RequestBody ClientDTO clientDTO) throws EmailAlreadyExistException, CinAlreadyExistException, PhoneAlreadyExistException{
		return clientService.save(clientDTO);
	}
	
	@PutMapping("/update/{id}")
	@ResponseBody
	ClientDTO update(@PathVariable(name="id") String id, @RequestBody ClientDTO clientDTO){
		return clientService.update(id, clientDTO);
	}
	
	@DeleteMapping("/deleteById/{id}")
	void deleteById(@PathVariable(name="id") String id){
		clientService.deleteById(id);
	}
	
	@DeleteMapping("/deleteByCin/{cin}")
	void deleteByCin(@PathVariable(name="cin") String cin){
		clientService.deleteByCin(cin);
	}
	
	@DeleteMapping("/deleteAll")
	void deleteAll(){
		clientService.deleteAll();
	}
	
	@PostMapping("/updatePassword")
	@ResponseBody
	void updatePassword(@RequestBody FormUpdatePassword form) {
		clientService.updatePassword(form.getId(), form.getOldPassword(), form.getNewPassword());
	}
	

}
