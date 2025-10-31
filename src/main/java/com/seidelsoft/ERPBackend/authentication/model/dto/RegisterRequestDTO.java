package com.seidelsoft.ERPBackend.authentication.model.dto;

import com.seidelsoft.ERPBackend.system.model.annotations.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

    @NotEmpty(message = "Nome não pode ser null ou em branco.")
    private String name;

    @NotEmpty
    @Email(message = "Email inválido")
    private String email;

    @Password
    @NotEmpty(message = "Senha não pode ser null ou em branco.")
    private String password;

}
