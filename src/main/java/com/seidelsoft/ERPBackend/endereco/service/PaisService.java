package com.seidelsoft.ERPBackend.endereco.service;

import com.seidelsoft.ERPBackend.endereco.model.Pais;
import com.seidelsoft.ERPBackend.endereco.repository.PaisRepository;
import com.seidelsoft.ERPBackend.system.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaisService extends BaseService<Pais, PaisRepository> {

    @Override
    public boolean validar(Pais entity) {
        if (entity.getNome() == null || entity.getNome().isEmpty()) {
            return false;
        }
        if (entity.getSigla() == null || entity.getSigla().isEmpty()) {
            return false;
        }
        return entity.getNomePortugues() != null && !entity.getNomePortugues().isEmpty();
    }

    @Override
    public void beforeSave(Pais item) {

    }

    @Override
    public void afterSave(Pais savedItem) {

    }

    @Override
    public void beforeDelete(Optional<Pais> item) {

    }

    @Override
    public void afterDelete() {

    }
}
