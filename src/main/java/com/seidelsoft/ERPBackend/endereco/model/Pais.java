package com.seidelsoft.ERPBackend.endereco.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pais")
public class Pais implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pais_generator")
	@SequenceGenerator(name = "pais_generator", sequenceName = "seq_pais", allocationSize = 1)
	private Long id;

	@Column(name = "nome", nullable = false, length = 255)
	private String nome;

	@Column(name = "nome_pt", nullable = false, length = 300)
	private String nomePortugues;

	@Column(name = "sigla", nullable = false, length = 2)
	private String sigla;
}
