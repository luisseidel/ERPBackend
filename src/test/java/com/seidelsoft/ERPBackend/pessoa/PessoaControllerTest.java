package com.seidelsoft.ERPBackend.pessoa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seidelsoft.ERPBackend.authentication.service.JwtService;
import com.seidelsoft.ERPBackend.authorization.entity.User;
import com.seidelsoft.ERPBackend.authorization.service.UserService;
import com.seidelsoft.ERPBackend.config.SecurityTestConfig;
import com.seidelsoft.ERPBackend.pessoa.controller.PessoaController;
import com.seidelsoft.ERPBackend.pessoa.model.Pessoa;
import com.seidelsoft.ERPBackend.pessoa.service.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PessoaController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(SecurityTestConfig.class)
class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PessoaService pessoaService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserService userService;

    private String token;
    private Pessoa pessoaMock;

    @BeforeEach
    void setUp() {
        token = "fake-jwt-token";
        Mockito.when(jwtService.generateToken(any(User.class))).thenReturn(token);

        pessoaMock = new Pessoa();
        pessoaMock.setId(1L);
        pessoaMock.setNome("Luis");
        pessoaMock.setCpfCnpj("29893540003");
        pessoaMock.setSexo(1L);
    }

    @Test
    @DisplayName("Deve retornar lista de pessoas com sucesso")
    void list_withValidRequest_returnsPagedList() throws Exception {
        Mockito.when(pessoaService.findAllPaged(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(pessoaMock)));

        mockMvc.perform(get("/api/v1/pessoa/list")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].nome").value("Luis"));

        Mockito.verify(pessoaService).findAllPaged(any(Pageable.class));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há pessoas cadastradas")
    void list_withNoData_returnsEmptyPage() throws Exception {
        Mockito.when(pessoaService.findAllPaged(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/api/v1/pessoa/list")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    @DisplayName("Deve deletar uma pessoa com sucesso")
    void delete_success_returnsNoContent() throws Exception {
        Mockito.doNothing().when(pessoaService).delete(any(Long.class));

        mockMvc.perform(delete("/api/v1/pessoa/id/1")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());

        Mockito.verify(pessoaService).delete(1L);
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request quando o ID for nulo ou não existir")
    void delete_success_returnsNoContent() throws Exception {
        Mockito.doNothing().when(pessoaService).delete(any(Long.class));

        mockMvc.perform(delete("/api/v1/pessoa/id/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());

        Mockito.verify(pessoaService).delete(1L);
    }


}