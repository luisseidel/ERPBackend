package com.seidelsoft.ERPBackend.config;

import com.seidelsoft.ERPBackend.authentication.service.JwtService;
import com.seidelsoft.ERPBackend.authorization.service.UserService;
import com.seidelsoft.ERPBackend.pessoa.controller.PessoaController;
import com.seidelsoft.ERPBackend.pessoa.service.PessoaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PessoaController.class)
class SecurityConfigurationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private PessoaService pessoaService;

    @Test
    @DisplayName("Deve retornar 401 quando não há token")
    void shouldReturn401_whenNoToken() throws Exception {
        mockMvc.perform(get("/api/v1/pessoa/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Deve retornar 401 quando token inválido")
    void shouldReturn401_whenInvalidToken() throws Exception {
        mockMvc.perform(get("/api/v1/pessoa/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer invalid"))
                .andExpect(status().isUnauthorized());
    }
}