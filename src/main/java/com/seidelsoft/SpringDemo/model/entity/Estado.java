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
@Table(name = "estados")
public class Estado implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estado_generator")
	@SequenceGenerator(name = "estado_generator", sequenceName = "seq_estados", allocationSize = 1)
	private Long id;

	@Column(name = "nome", length = 255, nullable = false)
	private String nome;

	@Column(name = "uf", length = 2, nullable = false)
	private String uf;

	@Column(name = "ibge", nullable = false)
	private Integer ibge;

	@ManyToOne
	@JoinColumn(name = "pais", referencedColumnName = "id")
	private Pais pais;
}
