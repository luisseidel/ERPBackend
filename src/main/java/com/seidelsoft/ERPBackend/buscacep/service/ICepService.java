package com.seidelsoft.ERPBackend.buscacep.service;

import com.seidelsoft.ERPBackend.endereco.model.Endereco;
import com.seidelsoft.ERPBackend.system.exception.ValidacaoException;

public interface ICepService {
    Object buscarCep(String cep) throws ValidacaoException;
    Endereco getEndereco(String cep) throws ValidacaoException;
    String getFormatedUrl(String cep);
    boolean isEnabled();
    boolean isFuncional();
}
