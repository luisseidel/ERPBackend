package com.seidelsoft.ERPBackend.model.dto.in;

import com.seidelsoft.ERPBackend.model.annotations.CpfCnpj;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PessoaFind {

    @NotEmpty
    @CpfCnpj
    private String cpf;
    @NotEmpty
    private String nome;

}
