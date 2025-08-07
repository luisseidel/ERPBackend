package com.seidelsoft.ERPBackend.buscacep.service;

import com.seidelsoft.ERPBackend.endereco.model.Endereco;
import com.seidelsoft.ERPBackend.endereco.repository.EnderecoRepository;
import com.seidelsoft.ERPBackend.system.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuscaCepService {

    @Autowired
    private ViaCepService viaCepService;
    @Autowired
    private BrasilApiService brasilApiService;
    @Autowired
    private EnderecoRepository enderecoRepository;


    public Endereco buscar(String cep) throws ValidacaoException {
        Endereco e = null;

        if (viaCepService.isFuncional()) {
            e = viaCepService.getEndereco(cep);
        }
        if (e == null && !viaCepService.isFuncional() && brasilApiService.isFuncional()) {
            e = brasilApiService.getEndereco(cep);
        }
        if (e != null) {
            e = enderecoRepository.save(e);
        }

        return e;
    }
}
