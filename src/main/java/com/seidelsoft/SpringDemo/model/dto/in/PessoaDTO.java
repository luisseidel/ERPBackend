package com.seidelsoft.SpringDemo.model.dto.in;

import com.seidelsoft.SpringDemo.model.enumerations.Sexo;
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

    private String cpf;
    private String nome;
	private EnderecoDTO endereco;
    private Sexo sexo;

}
