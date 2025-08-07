package com.seidelsoft.ERPBackend.endereco.model.dto;

import com.seidelsoft.ERPBackend.endereco.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO extends Endereco implements Serializable {

    private String complemento;
    private String numero;
    private String pontoReferencia;

}
