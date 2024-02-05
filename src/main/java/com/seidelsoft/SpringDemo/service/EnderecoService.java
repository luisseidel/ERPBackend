package com.seidelsoft.SpringDemo.service;

import com.seidelsoft.SpringDemo.model.dto.in.ViaCepDTO;
import com.seidelsoft.SpringDemo.model.entity.Endereco;
import com.seidelsoft.SpringDemo.repository.CidadeRepository;
import com.seidelsoft.SpringDemo.repository.EnderecoRepository;
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

	public Optional<Endereco> findById(Long id) {
		return enderecoRepository.findById(id);
	}

	private ViaCepDTO buscar(String cep) {
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

	public Endereco buscaCep(String cep) {
		Endereco e = enderecoRepository.findByCep(cep);

		if (e == null || e.getId() == null) {
			//encontrar no viacep
			ViaCepDTO dto = buscar(cep);

			e = new Endereco();
			e.setCep(dto.getCep());
			e.setLogradouro(dto.getLogradouro());
			e.setComplemento(dto.getComplemento());
			e.setCidade(cidadeRepository.findByIbge(dto.getIbge()));
			e.setBairro(dto.getBairro());

			e = enderecoRepository.save(e);
		}

		return e;
	}

}
