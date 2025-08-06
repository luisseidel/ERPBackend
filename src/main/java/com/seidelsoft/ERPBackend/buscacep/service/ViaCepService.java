package com.seidelsoft.ERPBackend.buscacep.service;

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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ViaCepService implements ICepService {

    @Value("${api.viacep.enabled}")
    private String enabled;

    @Value("${api.viacep.url}")
    private String urlViaCep;

    @Value("${api.viacep.endpoint}")
    private String endpointViaCep;

    private Boolean funcional;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Override
    public ViaCepDTO buscarCep(String cep) throws ValidacaoException {
        if (isEnabled() && isFuncional()) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<ViaCepDTO> response = restTemplate.getForEntity(
                        getFormatedUrl(cep),
                        ViaCepDTO.class);
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
        ViaCepDTO viaCepDTO = buscarCep(cep);

        if (viaCepDTO != null && viaCepDTO.getIbge() != null) {
            if (viaCepDTO.getCep().contains("-")) {
                viaCepDTO.setCep(viaCepDTO.getCep().replace("-", ""));
            }
            e.setCep(viaCepDTO.getCep());
            e.setLogradouro(viaCepDTO.getLogradouro());
            e.setComplemento(viaCepDTO.getComplemento());
            e.setCidade(cidadeRepository.findByIbge(viaCepDTO.getIbge()));
            e.setBairro(viaCepDTO.getBairro());
        }
        return e;
    }

    @Override
    public String getFormatedUrl(String cep) {
        return urlViaCep.concat(endpointViaCep.replace("{cep}", cep));
    }

    @Override
    public boolean isEnabled() {
        return StringUtils.isNotEmpty(enabled) && TrueFalse.TRUE.getDescricao().equalsIgnoreCase(enabled);
    }

    @Override
    public boolean isFuncional() {
        if (isEnabled()) {
            if (funcional == null) {
                try {
                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<String> response = restTemplate.getForEntity(urlViaCep, String.class);
                    log.info("Status VIACEP: " + response.getStatusCode().value());
                    funcional = HttpStatus.OK.value() == response.getStatusCode().value();
                } catch (HttpClientErrorException e) {
                    throw new RuntimeException("CEP invalido!" + e.getMessage());
                }
            }
            return funcional;
        }
        return false;
    }
}
