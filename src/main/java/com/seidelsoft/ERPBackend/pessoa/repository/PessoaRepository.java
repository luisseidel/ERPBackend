package com.seidelsoft.ERPBackend.pessoa.repository;

import com.seidelsoft.ERPBackend.pessoa.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	List<Pessoa> findByNome(String nome);

	Pessoa findByCpfCnpj(String cpf);

	boolean existsByCpfCnpj(String cpf);
}
