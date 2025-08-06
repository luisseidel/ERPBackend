package com.seidelsoft.ERPBackend.repository;

import com.seidelsoft.ERPBackend.model.entity.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

	Cidade findByIbge(String ibge);
	Cidade findByNomeAndEstado_Uf(String nome, String uf);

}
