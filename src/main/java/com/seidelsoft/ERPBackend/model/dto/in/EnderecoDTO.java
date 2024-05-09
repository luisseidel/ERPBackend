package com.seidelsoft.ERPBackend.model.dto.in;

import com.seidelsoft.ERPBackend.model.entity.Endereco;
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

    private String cep;
    private String complemento;
    private String numero;
    private String pontoReferencia;

}
