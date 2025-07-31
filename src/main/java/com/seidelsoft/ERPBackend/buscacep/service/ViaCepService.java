package com.seidelsoft.ERPBackend.buscacep.service;

import com.seidelsoft.ERPBackend.buscacep.model.ViaCepDTO;
import com.seidelsoft.ERPBackend.model.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService implements ICepService {

    @Value("${api.viacep.enabled}")
    private String enabled;

    @Value("${api.viacep.url}")
    private String urlViaCep;

    @Override
    public ViaCepDTO buscarCep(String cep) throws ValidacaoException {
        if ("true".equalsIgnoreCase(enabled)) {
            RestTemplate restTemplate = new RestTemplate();
            try {
                ResponseEntity<ViaCepDTO> response = restTemplate.getForEntity(
                        String.format(urlViaCep, cep),
                        ViaCepDTO.class);
                return response.getBody();
            } catch (HttpClientErrorException e) {
                throw new RuntimeException("CEP invalido!" + e.getMessage());
            }
        } else {
            throw new ValidacaoException("Integração Desabilitada!");
        }
    }
}
