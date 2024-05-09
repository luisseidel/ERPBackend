package com.seidelsoft.ERPBackend.service;

import com.seidelsoft.ERPBackend.model.dto.in.PessoaDTO;
import com.seidelsoft.ERPBackend.model.entity.Endereco;
import com.seidelsoft.ERPBackend.model.entity.Pessoa;
import com.seidelsoft.ERPBackend.model.exception.ValidacaoException;
import com.seidelsoft.ERPBackend.repository.PessoaRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repository;
	@Autowired
	private EnderecoService enderecoService;

	public Optional<Pessoa> getPessoaById(Long id) {
		return repository.findById(id);
	}

	public List<Pessoa> getPessoaBy(String nome, String cpf) throws ValidacaoException {
		if (StringUtils.isNotEmpty(cpf) && StringUtils.isEmpty(nome)) {
			return repository.findByCpf(cpf);

		} else if (StringUtils.isEmpty(cpf) && StringUtils.isNotEmpty(nome)) {
			return repository.findByNome(nome);

		} else {
			throw new ValidacaoException("Nenhum nome ou cpf fornecidos para buscar");
		}
	}

	public ResponseEntity create(PessoaDTO dto) {
		ResponseEntity re = validatePessoaDTO(dto);
		if (re != null) {
			return re;
		}

		Endereco endereco = dto.getEndereco();
		if (StringUtils.isNotEmpty(dto.getEndereco().getCep())) {
			endereco = enderecoService.buscaByCep(dto.getEndereco().getCep());
			endereco.setNumero(dto.getEndereco().getNumero());
			endereco.setComplemento(dto.getEndereco().getComplemento());
			endereco.setPontoReferencia(dto.getEndereco().getPontoReferencia());
		}

		Pessoa pessoa = new Pessoa(dto.getCpf(), dto.getNome(), endereco, dto.getSexo());

		re = validatePessoa(pessoa);
		if (re != null) {
			return re;
		}

		pessoa = repository.save(pessoa);

		return ResponseEntity.ok(pessoa);
	}

	public ResponseEntity update(Long id, PessoaDTO dto) throws ValidacaoException {
		ResponseEntity re = validatePessoaDTO(dto);
		if (re != null) {
			return re;
		}

		Pessoa pessoa = repository.getReferenceById(id);
		if (pessoa == null || pessoa.getId() == null) {
			throw new ValidacaoException("Pessoa não encontrada com o código " + id);
		}

		pessoa.setCpf(dto.getCpf());
		pessoa.setSexo(dto.getSexo());
		pessoa.setNome(dto.getNome());
		pessoa.setEndereco(dto.getEndereco());

		re = validatePessoa(pessoa);
		if (re != null) {
			return re;
		}

		pessoa = repository.save(pessoa);

		return ResponseEntity.ok(pessoa);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	private ResponseEntity validatePessoaDTO(PessoaDTO dto) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<PessoaDTO>> violations = validator.validate(dto);

		if (!violations.isEmpty()) {
			return ResponseEntity.badRequest().body(violations);
		}
		return null;
	}

	private ResponseEntity validatePessoa(Pessoa pessoa) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Pessoa>> violations = validator.validate(pessoa);

		if (!violations.isEmpty()) {
			return ResponseEntity.badRequest().body(violations);
		}
		return null;
	}

}
