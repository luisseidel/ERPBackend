package com.seidelsoft.SpringDemo.model.entity;

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
@Table(name = "paises")
public class Pais implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pais_generator")
	@SequenceGenerator(name = "pais_generator", sequenceName = "seq_paises", allocationSize = 1)
	private Long id;

	@Column(name = "nome", nullable = false, length = 255)
	private String nome;

	@Column(name = "nome_pt", nullable = false, length = 300)
	private String nomePortugues;

	@Column(name = "iso", nullable = false, length = 2)
	private String iso;
}
