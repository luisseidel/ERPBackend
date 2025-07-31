package com.seidelsoft.ERPBackend.buscacep.service;

import com.seidelsoft.ERPBackend.buscacep.model.BrasilApiCepDTO;
import com.seidelsoft.ERPBackend.buscacep.model.ViaCepDTO;
import com.seidelsoft.ERPBackend.model.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuscaCepService {

    @Autowired
    private ViaCepService viaCepService;

    @Autowired
    private BrasilApiService brasilApiService;

    public ViaCepDTO buscarViaCep(String cep) throws ValidacaoException {
        return viaCepService.buscarCep(cep);
    }

    public BrasilApiCepDTO buscarBrasilApi(String cep) throws ValidacaoException {
        return brasilApiService.buscarCep(cep);
    }
}
