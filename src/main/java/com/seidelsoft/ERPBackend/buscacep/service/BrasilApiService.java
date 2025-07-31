package com.seidelsoft.ERPBackend.buscacep.service;

import com.seidelsoft.ERPBackend.buscacep.model.BrasilApiCepDTO;
import com.seidelsoft.ERPBackend.model.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class BrasilApiService implements ICepService {

    @Value("${api.brasilApi.enabled}")
    private String enabled;

    @Value("${api.brasilApi.url}")
    private String urlBrasilApi;

    @Override
    public BrasilApiCepDTO buscarCep(String cep) throws ValidacaoException {
        if ("true".equalsIgnoreCase(enabled)) {
            RestTemplate restTemplate = new RestTemplate();
            try {
                ResponseEntity<BrasilApiCepDTO> response = restTemplate.getForEntity(
                        String.format(urlBrasilApi+"/cep/v2/%s", cep),
                        BrasilApiCepDTO.class);
                return response.getBody();
            } catch (HttpClientErrorException e) {
                throw new RuntimeException("CEP invalido!" + e.getMessage());
            }
        } else {
            throw new ValidacaoException("Integração Desabilitada!");
        }
    }
}
