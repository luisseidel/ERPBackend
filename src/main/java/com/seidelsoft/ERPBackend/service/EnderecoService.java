package com.seidelsoft.ERPBackend.service;

import com.seidelsoft.ERPBackend.model.dto.in.ViaCepDTO;
import com.seidelsoft.ERPBackend.model.entity.Endereco;
import com.seidelsoft.ERPBackend.repository.CidadeRepository;
import com.seidelsoft.ERPBackend.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;

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

	private ViaCepDTO buscarViaCep(String cep) {
		RestTemplate restTemplate = new RestTemplate();
		try {
			ResponseEntity<ViaCepDTO> response = restTemplate.getForEntity(
					String.format("https://viacep.com.br/ws/%s/json", cep),
					ViaCepDTO.class);
			return response.getBody();
		} catch (HttpClientErrorException e) {
			throw new RuntimeException("CEP invalido!" + e.getMessage());
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

	public Endereco buscaByCep(String cep) {
		Endereco e = enderecoRepository.findByCep(cep);

		if (e == null || e.getId() == null) {
			ViaCepDTO dto = buscarViaCep(cep);

			e = new Endereco(
					dto.getCep(),
					dto.getLogradouro(),
					dto.getComplemento(),
					cidadeRepository.findByIbge(dto.getIbge()),
					dto.getBairro()
			);

			e = enderecoRepository.save(e);
		}

		return e;
	}
}
