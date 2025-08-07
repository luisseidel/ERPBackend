package com.seidelsoft.ERPBackend.pessoa.model.dto;

import com.seidelsoft.ERPBackend.pessoa.model.annotations.CpfCnpj;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PessoaFindDTO {

    @NotEmpty
    @CpfCnpj
    private String cpf;
    @NotEmpty
    private String nome;

}
