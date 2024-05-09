package com.seidelsoft.ERPBackend.repository;

import com.seidelsoft.ERPBackend.model.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	List<Pessoa> findByNome(String nome);

	List<Pessoa> findByCpf(String cpf);
}
