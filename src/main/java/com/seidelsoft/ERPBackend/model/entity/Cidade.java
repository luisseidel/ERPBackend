package com.seidelsoft.ERPBackend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "cidade")
public class Cidade implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cidade_generator")
	@SequenceGenerator(name = "cidade_generator", sequenceName = "seq_cidade", allocationSize = 1)
	private Long id;

	@Column(name = "nome", length = 255, nullable = false)
	@NotNull(message = "Nome não pode ser null")
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
