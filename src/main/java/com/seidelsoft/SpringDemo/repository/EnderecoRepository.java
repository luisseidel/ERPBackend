package com.seidelsoft.SpringDemo.repository;

import com.seidelsoft.SpringDemo.model.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

	Endereco findByCep(String cep);

}
