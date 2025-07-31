package com.seidelsoft.ERPBackend.service;

import com.seidelsoft.ERPBackend.model.exception.ValidacaoException;
import com.seidelsoft.ERPBackend.buscacep.model.BrasilApiCepDTO;
import com.seidelsoft.ERPBackend.buscacep.model.ViaCepDTO;
import com.seidelsoft.ERPBackend.model.entity.Endereco;
import com.seidelsoft.ERPBackend.repository.CidadeRepository;
import com.seidelsoft.ERPBackend.repository.EnderecoRepository;
import com.seidelsoft.ERPBackend.buscacep.service.BuscaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
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
		Endereco e = enderecoRepository.findByCep(cep);

		BrasilApiCepDTO bdto = buscaCepService.buscarBrasilApi(cep);

		System.out.println(bdto.toString());
		if (e == null || e.getId() == null) {
			ViaCepDTO dto = buscaCepService.buscarViaCep(cep);

			if (dto.getCep().contains("-")) {
				dto.setCep(dto.getCep().replace("-", ""));
			}

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
