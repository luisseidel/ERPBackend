package com.seidelsoft.ERPBackend.buscacep.service;

import com.seidelsoft.ERPBackend.buscacep.model.BrasilApiCepDTO;
import com.seidelsoft.ERPBackend.buscacep.model.ViaCepDTO;
import com.seidelsoft.ERPBackend.model.entity.Endereco;
import com.seidelsoft.ERPBackend.model.exception.ValidacaoException;
import com.seidelsoft.ERPBackend.repository.CidadeRepository;
import com.seidelsoft.ERPBackend.repository.EnderecoRepository;
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
