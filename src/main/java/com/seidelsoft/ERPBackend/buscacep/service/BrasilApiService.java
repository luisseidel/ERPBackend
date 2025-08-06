package com.seidelsoft.ERPBackend.buscacep.service;

import com.seidelsoft.ERPBackend.buscacep.model.BrasilApiCepDTO;
import com.seidelsoft.ERPBackend.buscacep.model.ViaCepDTO;
import com.seidelsoft.ERPBackend.model.entity.Endereco;
import com.seidelsoft.ERPBackend.model.enumerations.TrueFalse;
import com.seidelsoft.ERPBackend.model.exception.ValidacaoException;
import com.seidelsoft.ERPBackend.repository.CidadeRepository;
import com.seidelsoft.ERPBackend.repository.EnderecoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class BrasilApiService implements ICepService {

    @Value("${api.brasilApi.enabled}")
    private String enabled;

    @Value("${api.brasilApi.url}")
    private String urlBrasilApi;

    @Value("${api.brasilApi.endpoint}")
    private String endpointBrasilApi;

    private Boolean funcional;

    @Autowired
    private CidadeRepository cidadeRepository;


    @Override
    public BrasilApiCepDTO buscarCep(String cep) throws ValidacaoException {
        if (isEnabled() && isFuncional()) {
            RestTemplate restTemplate = new RestTemplate();
            try {
                ResponseEntity<BrasilApiCepDTO> response = restTemplate.getForEntity(
                        getFormatedUrl(cep),
                        BrasilApiCepDTO.class);
                return response.getBody();
            } catch (HttpClientErrorException e) {
                throw new RuntimeException("CEP invalido!" + e.getMessage());
            }
        } else {
            throw new ValidacaoException("Integração Desabilitada!");
        }
    }

    @Override
    public Endereco getEndereco(String cep) throws ValidacaoException {
        Endereco e = new Endereco();
        BrasilApiCepDTO dto = buscarCep(cep);

        if (dto != null && dto.getCity() != null) {
            if (dto.getCep().contains("-")) {
                dto.setCep(dto.getCep().replace("-", ""));
            }
            e.setCep(dto.getCep());
            e.setLogradouro(dto.getStreet());
            e.setCidade(cidadeRepository.findByNomeAndEstado_Uf(dto.getCity(), dto.getState()));
            e.setBairro(dto.getNeighborhood());
        }
        return e;
    }

    @Override
    public String getFormatedUrl(String cep) {
        return urlBrasilApi.concat(endpointBrasilApi.replace("{cep}", cep));
    }

    @Override
    public boolean isFuncional() {
        if (funcional == null) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> response = restTemplate.getForEntity(urlBrasilApi, String.class);
                log.info("Status BrasilAPI: " + response.getStatusCode().value());
                funcional = HttpStatus.OK.value() == response.getStatusCode().value();
                return funcional;
            } catch (HttpClientErrorException e) {
                throw new RuntimeException("Brasil API não está funcional!" + e.getMessage());
            }
        }
        return funcional;
    }

    @Override
    public boolean isEnabled() {
        return StringUtils.isNotEmpty(enabled) && TrueFalse.TRUE.getDescricao().equalsIgnoreCase(enabled);
    }
}
