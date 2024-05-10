package com.seidelsoft.ERPBackend.model.entity;

import com.seidelsoft.ERPBackend.model.annotations.CpfCnpj;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "pessoa")
public class Pessoa implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_generator")
    @SequenceGenerator(name = "pessoa_generator", sequenceName = "seq_pessoa", allocationSize = 1)
    private Long id;

	@CpfCnpj
	@Column(name = "cpf_cnpj", length = 14, nullable = false)
	@NotEmpty(message = "CPF / CNPJ não pode ser null ou em branco.")
	private String cpfCnpj;

	@Column(name = "nome", length = 255, nullable = false)
	@NotEmpty(message = "Nome não pode ser null ou em branco.")
    private String nome;

	@Column(name = "sexo", nullable = false)
	@NotNull(message = "Sexo não pode ser null ou em branco.")
	private Long sexo;

	@Column(name = "email", length = 255, nullable = true)
	@Email(message = "Email inválido")
	private String email;

	@ManyToOne
	@JoinColumn(name = "id_endereco", referencedColumnName = "id")
	private Endereco endereco;

}
