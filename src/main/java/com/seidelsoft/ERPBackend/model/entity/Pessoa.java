package com.seidelsoft.ERPBackend.model.entity;

import com.seidelsoft.ERPBackend.model.annotations.CpfCnpj;
import com.seidelsoft.ERPBackend.model.enumerations.Sexo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name = "pessoa")
public class Pessoa implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_generator")
    @SequenceGenerator(name = "pessoa_generator", sequenceName = "seq_pessoa", allocationSize = 1)
    private Long id;

	@CpfCnpj
	@Column(name = "cpf", length = 11, nullable = false)
	@NotEmpty(message = "CPF não pode ser null ou em branco.")
    private String cpf;

	@Column(name = "nome", length = 255, nullable = false)
	@NotEmpty(message = "Nome não pode ser null ou em branco.")
    private String nome;

	@Column(name = "sexo", nullable = false)
    @Enumerated(EnumType.ORDINAL)
	@NotEmpty(message = "Sexo não pode ser null ou em branco.")
    private Sexo sexo;

	@Column(name = "email", length = 255, nullable = true)
	@Email(message = "Email inválido")
	private String email;

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
