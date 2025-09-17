package com.seidelsoft.ERPBackend.pessoa.model;

import com.seidelsoft.ERPBackend.endereco.model.Endereco;
import com.seidelsoft.ERPBackend.pessoa.model.annotations.CpfCnpj;
import com.seidelsoft.ERPBackend.system.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pessoa")
@SequenceGenerator(name = "pessoa_generator", sequenceName = "seq_pessoa", allocationSize = 1)
public class Pessoa extends BaseEntity {

    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_generator")
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
