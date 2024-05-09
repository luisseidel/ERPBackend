package com.seidelsoft.ERPBackend.repository;

import com.seidelsoft.ERPBackend.model.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

	Endereco findByCep(String cep);

}
