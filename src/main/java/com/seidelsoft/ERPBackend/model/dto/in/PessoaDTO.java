package com.seidelsoft.ERPBackend.model.dto.in;

import com.seidelsoft.ERPBackend.model.enumerations.Sexo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO implements Serializable {

    @NotEmpty(message = "CPF não pode ser null ou em branco.")
    private String cpf;
    @NotEmpty(message = "Nome não pode ser null ou em branco.")
    private String nome;
    private Sexo sexo;
    @Email(message = "Email inválido")
    private String email;
    private EnderecoDTO endereco;

}
