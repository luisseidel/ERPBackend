package com.seidelsoft.ERPBackend.endereco.service;

import com.seidelsoft.ERPBackend.endereco.model.Pais;
import com.seidelsoft.ERPBackend.endereco.repository.PaisRepository;
import com.seidelsoft.ERPBackend.system.service.CachableService;
import org.springframework.stereotype.Service;

@Service
public class PaisService extends CachableService<Pais, PaisRepository> {

    @Override
    public boolean validar(Pais entity, StringBuilder msgValidacao) {
        if (entity.getNome() == null || entity.getNome().isEmpty()) {
            return false;
        }
        if (entity.getSigla() == null || entity.getSigla().isEmpty()) {
            return false;
        }
        if (entity.getNomePortugues() == null || entity.getNomePortugues().isEmpty()) {
            return false;
        }
        return msgValidacao.isEmpty();
    }

}
