package com.seidelsoft.SpringDemo.service;

import com.seidelsoft.SpringDemo.model.dto.in.PessoaDTO;
import com.seidelsoft.SpringDemo.model.entity.Pessoa;
import com.seidelsoft.SpringDemo.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repository;

	public Optional<Pessoa> getPessoa(@PathVariable Long id) {
		return repository.findById(id);
	}

	@GetMapping("/{name}")
	public List<Pessoa> getPessoa(@PathVariable String name) {
		return repository.findByNome(name);
	}

	public Pessoa create(@RequestBody PessoaDTO dto) {
		return repository.save(new Pessoa(dto.getCpf(), dto.getNome(), dto.getEndereco(), dto.getSexo()));
	}

	public Pessoa update(@PathVariable Long id, @RequestBody PessoaDTO dto) {
		Pessoa c = repository.getReferenceById(id);
		c.setCpf(dto.getCpf());
		c.setSexo(dto.getSexo());
		c.setNome(dto.getNome());
		c.setEndereco(dto.getEndereco());

		return repository.save(c);
	}

	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}

}
