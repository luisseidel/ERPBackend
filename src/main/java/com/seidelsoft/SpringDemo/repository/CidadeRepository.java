package com.seidelsoft.SpringDemo.repository;

import com.seidelsoft.SpringDemo.model.entity.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

	Cidade findByIbge(String ibge);

}
