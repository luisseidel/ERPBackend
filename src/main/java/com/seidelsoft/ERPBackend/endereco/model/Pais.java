package com.seidelsoft.ERPBackend.endereco.model;

import com.seidelsoft.ERPBackend.system.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pais")
@SequenceGenerator(name = "pais_generator", sequenceName = "seq_pais", allocationSize = 1)
public class Pais extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pais_generator")
    private Long id;

	@Column(name = "nome", nullable = false, length = 255)
	private String nome;

	@Column(name = "nome_pt", nullable = false, length = 300)
	private String nomePortugues;

	@Column(name = "sigla", nullable = false, length = 2)
	private String sigla;
}
