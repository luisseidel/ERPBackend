package com.seidelsoft.SpringDemo.model.entity;

import com.seidelsoft.SpringDemo.model.enumerations.Sexo;
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
@Table(name = "pessoas")
public class Pessoa implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_generator")
	@SequenceGenerator(name = "pessoa_generator", sequenceName = "seq_pessoas", allocationSize = 1)
    private Long id;

	@Column(name = "cpf", length = 11, nullable = false)
    private String cpf;

	@Column(name = "nome", length = 255, nullable = false)
    private String nome;

	@Column(name = "flag_nome_social", columnDefinition = "boolean")
	private boolean flagNomeSocial;

	@Column(name = "nome_social", length = 255, nullable = true)
	private String nomeSocial;

	@Column(name = "sexo", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Sexo sexo;

	@ManyToOne
	@JoinColumn(name = "endereco", referencedColumnName = "id")
	private Endereco endereco;

	public Pessoa(String cpf, String nome, Endereco endereco, Sexo sexo) {
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.sexo = sexo;
    }
}
