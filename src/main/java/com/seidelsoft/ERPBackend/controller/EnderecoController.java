package com.seidelsoft.ERPBackend.controller;

import com.seidelsoft.ERPBackend.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/enderecos")
public class EnderecoController implements SecuredController {


	@Autowired
	private EnderecoService service;

	@GetMapping("/id/{id}")
	public ResponseEntity getEndereco(@PathVariable Long id) {
		return service.findById(id);
	}

	@GetMapping("/cep/{cep}")
	public ResponseEntity getEndereco(@PathVariable String cep) {
		return service.findByCep(cep);
	}

}
