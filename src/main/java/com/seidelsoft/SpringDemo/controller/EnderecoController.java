package com.seidelsoft.SpringDemo.controller;

import com.seidelsoft.SpringDemo.model.entity.Endereco;
import com.seidelsoft.SpringDemo.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/enderecos")
public class EnderecoController implements SecuredController {


	@Autowired
	private EnderecoService service;

	@GetMapping("/{id}")
	public Optional<Endereco> getEndereco(@PathVariable Long id) {
		return service.findById(id);
	}

	@GetMapping("/{cep}")
	public Endereco getEndereco(@PathVariable String cep) {
		return service.buscaCep(cep);
	}

}
