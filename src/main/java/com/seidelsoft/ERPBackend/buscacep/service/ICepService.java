package com.seidelsoft.ERPBackend.buscacep.service;

import com.seidelsoft.ERPBackend.model.exception.ValidacaoException;

public interface ICepService {
    public Object buscarCep(String cep) throws ValidacaoException;
}
