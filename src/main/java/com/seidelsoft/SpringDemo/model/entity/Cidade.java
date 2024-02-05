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
@Table(name = "cidades")
public class Cidade implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cidade_generator")
	@SequenceGenerator(name = "cidade_generator", sequenceName = "seq_cidades", allocationSize = 1)
	private Long id;

	@Column(name = "nome", length = 255, nullable = false)
	private String nome;

	@Column(name = "ibge", length = 10, nullable = false)
	private String ibge;

	@Column(name = "latitude", nullable = true)
	private Double latitude;

	@Column(name = "longitude", nullable = true)
	private Double longitude;

	@ManyToOne
	@JoinColumn(name = "estado", referencedColumnName = "id")
	private Estado pais;

}
