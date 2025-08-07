package com.seidelsoft.ERPBackend.pessoa.service;

import com.seidelsoft.ERPBackend.endereco.model.Endereco;
import com.seidelsoft.ERPBackend.endereco.service.EnderecoService;
import com.seidelsoft.ERPBackend.pessoa.model.Pessoa;
import com.seidelsoft.ERPBackend.pessoa.repository.PessoaRepository;
import com.seidelsoft.ERPBackend.system.exception.ValidacaoException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repository;
	@Autowired
	private EnderecoService enderecoService;

	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();

	public Optional<Pessoa> getPessoaById(Long id) {
		return repository.findById(id);
	}

	public List<Pessoa> getPessoaBy(String nome, String cpf) throws ValidacaoException {
		List<Pessoa> pessoas = new ArrayList<>();
		if (StringUtils.isNotEmpty(cpf) && StringUtils.isEmpty(nome)) {
			pessoas.add(repository.findByCpfCnpj(cpf));

		} else if (StringUtils.isEmpty(cpf) && StringUtils.isNotEmpty(nome)) {
			pessoas.addAll(repository.findByNome(nome));

		} else if (StringUtils.isNotEmpty(cpf) && StringUtils.isNotEmpty(nome)) {
			pessoas.add(repository.findByCpfCnpj(cpf));

		} else {
			throw new ValidacaoException("Nenhum nome ou cpf fornecidos para buscar");
		}

		return pessoas;
	}

	public Pessoa create(Pessoa dto) throws ValidacaoException {
		StringBuilder validacoes = new StringBuilder();
		validatePessoa(dto, validacoes);

		Endereco endereco = dto.getEndereco();
		if (StringUtils.isNotEmpty(dto.getEndereco().getCep())) {
			endereco = enderecoService.buscaByCep(dto.getEndereco().getCep());
			endereco.setNumero(dto.getEndereco().getNumero());
			endereco.setComplemento(dto.getEndereco().getComplemento());
			endereco.setPontoReferencia(dto.getEndereco().getPontoReferencia());
		}

		validateEndereco(endereco, validacoes);

		if (StringUtils.isNotEmpty(validacoes)) {
			throw new ValidacaoException(validacoes.toString());
		}

		Pessoa pessoa = new Pessoa();
		pessoa.setCpfCnpj(dto.getCpfCnpj());
		pessoa.setNome(dto.getNome());
		pessoa.setSexo(dto.getSexo());
		pessoa.setEndereco(endereco);
		pessoa.setEmail(dto.getEmail());
		pessoa = repository.save(pessoa);

		return pessoa;
	}

	public Pessoa update(String cpf, Pessoa dto) throws ValidacaoException {
		StringBuilder validacoes = new StringBuilder();
		validatePessoa(dto, validacoes);

		Endereco endereco = dto.getEndereco();
		if (StringUtils.isNotEmpty(dto.getEndereco().getCep())) {
			endereco = enderecoService.buscaByCep(dto.getEndereco().getCep());
			endereco.setNumero(dto.getEndereco().getNumero());
			endereco.setComplemento(dto.getEndereco().getComplemento());
			endereco.setPontoReferencia(dto.getEndereco().getPontoReferencia());
		}
		validateEndereco(endereco, validacoes);

		if (StringUtils.isNotEmpty(validacoes)) {
			throw new ValidacaoException(validacoes.toString());
		}

		Pessoa pessoa = repository.findByCpfCnpj(cpf);
		if (pessoa == null || pessoa.getId() == null) {
			throw new ValidacaoException("Pessoa não encontrada com o cpf/cnpj " + cpf);
		}

		pessoa.setCpfCnpj(dto.getCpfCnpj());
		pessoa.setNome(dto.getNome());
		pessoa.setSexo(dto.getSexo());
		pessoa.setEndereco(endereco);
		pessoa.setEmail(dto.getEmail());

		pessoa = repository.save(pessoa);

		return pessoa;
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	private void validatePessoa(Pessoa pessoa, StringBuilder sb) {
		Set<ConstraintViolation<Pessoa>> violationsPessoa = validator.validate(pessoa);
		if (!violationsPessoa.isEmpty()) {
			for (ConstraintViolation<Pessoa> violation : violationsPessoa) {
				sb.append(violation.getMessage());
				sb.append(System.lineSeparator());
			}
		}

		if (StringUtils.isNotEmpty(pessoa.getCpfCnpj()) && repository.existsByCpfCnpj(pessoa.getCpfCnpj())) {
			sb.append("Já existe um cadastro com esse CPF!");
		}
	}

	private void validateEndereco(Endereco endereco, StringBuilder sb) {
		Set<ConstraintViolation<Endereco>> violationsEndereco = validator.validate(endereco);
		if (!violationsEndereco.isEmpty()) {
			for (ConstraintViolation<Endereco> violation : violationsEndereco) {
				sb.append(violation.getMessage());
				sb.append(System.lineSeparator());
			}
		}
	}

}
