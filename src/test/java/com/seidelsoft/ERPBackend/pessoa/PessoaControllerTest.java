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
import org.junit.jupiter.api.Nested;
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
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityTestConfig.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = PessoaController.class)
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

    @Nested
    @DisplayName("GET /api/v1/pessoa/list")
    class ListTest {
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
        @DisplayName("Ao não informar parâmetros na consulta, deve retornar uma lista com os valores default, de page (0) e pageSize (10)")
        void list_whenMissingParams_returnsPagedList() throws Exception {
            Mockito.when(pessoaService.findAllPaged(any(Pageable.class)))
                    .thenReturn(new PageImpl<>(List.of(pessoaMock)));

            mockMvc.perform(get("/api/v1/pessoa/list")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isNotEmpty());
        }
    }

    @Nested
    @DisplayName("GET /api/v1/pessoa/id/{id}")
    class GetByIdTest {

        @Test
        @DisplayName("Deve retornar uma pessoa")
        void getById_should_return_pessoa() throws Exception {
            Mockito.when(pessoaService.getById(any(Long.class))).thenReturn(Optional.of(pessoaMock));

            mockMvc.perform(get("/api/v1/pessoa/id/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

            Mockito.verify(pessoaService).getById(1L);
        }

        @Test
        @DisplayName("Deve retonar 404 - not found")
        void getById_whenWrongEndpoint_returnsNotFound() throws Exception {
            mockMvc.perform(get("/api/v1/pessoa/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + token))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Deve retornar 204 - no content")
        void getById_whenMissingDatabseObject_returnsNoContent() throws Exception {
            Mockito.when(pessoaService.getById(any(Long.class))).thenReturn(java.util.Optional.empty());

            mockMvc.perform(get("/api/v1/pessoa/id/22222")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + token))
                    .andExpect(status().isNoContent());

            Mockito.verify(pessoaService).getById(22222L);
        }
    }

    @Nested
    @DisplayName("POST /api/v1/pessoa")
    class CreateTest {
        @Test
        @DisplayName("Deve criar uma pessoa com sucesso")
        void create_success_returnsPessoa() throws Exception {
            Mockito.when(pessoaService.createUpdate(any(Pessoa.class))).thenReturn(pessoaMock);

            mockMvc.perform(post("/api/v1/pessoa")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + token)
                            .content(objectMapper.writeValueAsString(pessoaMock)))
                    .andExpect(status().isCreated())
                    .andExpect(header().exists("Location"))
                    .andExpect(header().string("Location", containsString("/api/v1/pessoa/1")));
            ;
        }

        @Test
        @DisplayName("Ao não informar o objeto, deve retornar 400 - bad request")
        void create_whenMissingObject_returnsBadRequest() throws Exception {
            Mockito.when(pessoaService.createUpdate(any(null))).thenReturn(pessoaMock);

            mockMvc.perform(post("/api/v1/pessoa")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + token)
                    )
                    .andExpect(status().isBadRequest())
            ;
        }
    }

    @Nested
    @DisplayName("PUT /api/v1/pessoa")
    class UpdateTest {
        @Test
        @DisplayName("Deve atualizar uma pessoa com sucesso")
        void update_sucess_returnsPessoa() throws Exception {
            Mockito.when(pessoaService.createUpdate(any(Pessoa.class))).thenReturn(pessoaMock);

            mockMvc.perform(put("/api/v1/pessoa")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + token)
                            .content(objectMapper.writeValueAsString(pessoaMock)))
                    .andExpect(status().isOk())
            ;
        }
    }


    @Nested
    @DisplayName("DELETE /api/v1/pessoa/id/{id}")
    class DeleteTest {
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
        @DisplayName("Não deve fazer nada caso a pessoa não exista no banco, retornando 204 - sem conteúdo.")
        void delete_whenMissingDatabseObject_returnsNoContent() throws Exception {
            Mockito.doNothing().when(pessoaService).delete(any(Long.class));

            mockMvc.perform(delete("/api/v1/pessoa/id/9999")
                            .header("Authorization", "Bearer " + token))
                    .andExpect(status().isNoContent());

            Mockito.verify(pessoaService).delete(9999L);
        }

        @Test
        @DisplayName("Deve retornar 404 - not found, caso não encontre o endpoint")
        void delete_onWrongEndpoint_returnsBadRequest() throws Exception {
            mockMvc.perform(delete("/api/v1/pessoa/9999")
                            .header("Authorization", "Bearer " + token))
                    .andExpect(status().isNotFound());
        }
    }

}