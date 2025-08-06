package com.seidelsoft.ERPBackend.service;

import com.seidelsoft.ERPBackend.buscacep.service.BuscaCepService;
import com.seidelsoft.ERPBackend.model.entity.Endereco;
import com.seidelsoft.ERPBackend.model.exception.ValidacaoException;
import com.seidelsoft.ERPBackend.repository.EnderecoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private BuscaCepService buscaCepService;

	public ResponseEntity findById(Long id) {
		try {
			Optional<Endereco> endereco = enderecoRepository.findById(id);
			if (endereco.isPresent()) {
				return ResponseEntity.ok(endereco.get());
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	public ResponseEntity findByCep(String cep) {
		try {
			Endereco e = buscaByCep(cep);
			return ResponseEntity.ok(e);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	public Endereco buscaByCep(String cep) throws ValidacaoException {
		Endereco e = null; //enderecoRepository.findByCep(cep);

		if (e == null || e.getId() == null) {
			e = buscaCepService.buscar(cep);
		}

		return e;
	}
}
