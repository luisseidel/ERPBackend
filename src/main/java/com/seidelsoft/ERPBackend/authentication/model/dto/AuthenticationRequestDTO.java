package com.seidelsoft.ERPBackend.authentication.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDTO {

    @NotEmpty(message = "Email não pode estar null ou em branco!")
    private String email;

    @NotEmpty(message = "Senha não pode estar null ou em branco!")
    private String password;

}
