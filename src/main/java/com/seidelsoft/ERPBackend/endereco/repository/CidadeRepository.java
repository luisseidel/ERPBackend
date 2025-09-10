package com.seidelsoft.ERPBackend.endereco.repository;

import com.seidelsoft.ERPBackend.endereco.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

	Cidade findByIbge(String ibge);
	Cidade findByNomeAndEstado_Uf(String nome, String uf);

}
