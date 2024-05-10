package com.seidelsoft.ERPBackend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "endereco")
public class Endereco implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco_generator")
	@SequenceGenerator(name = "endereco_generator", sequenceName = "seq_endereco", allocationSize = 1)
	private Long id;

	@NotEmpty(message = "Cep é obrigatório e não pode estar em branco.")
	@Column(name = "cep", length = 8, nullable = false)
	private String cep;

	@NotEmpty(message = "Logradouro é obrigatório e não pode estar em branco.")
	@Column(name = "logradouro", length = 200, nullable = false)
	private String logradouro;

	@Column(name = "numero", length = 10, nullable = true)
	private String numero;

	@Column(name = "bairro", length = 200, nullable = true)
	private String bairro;

	@Column(name = "complemento", length = 200, nullable = true)
	private String complemento;

	@Column(name = "ponto_referencia", length = 200, nullable = true)
	private String pontoReferencia;

	@ManyToOne
	@JoinColumn(name = "cidade", referencedColumnName = "id")
	private Cidade cidade;


	public Endereco(String cep) {
		this.cep = cep;
	}

	public Endereco(String cep, String logradouro, String complemento, Cidade cidade, String bairro) {
		this.cep = cep;
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.complemento = complemento;
		this.cidade = cidade;
	}
}
