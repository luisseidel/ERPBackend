package com.seidelsoft.ERPBackend.endereco.service;

import com.seidelsoft.ERPBackend.endereco.model.Estado;
import com.seidelsoft.ERPBackend.endereco.repository.EstadoRepository;
import com.seidelsoft.ERPBackend.system.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstadoService extends BaseService<Estado, EstadoRepository> {

    @Override
    public boolean validar(Estado entity) {
        if (entity.getNome() == null || entity.getNome().isEmpty()) {
            return false;
        }
        if (entity.getUf() == null || entity.getUf().isEmpty()) {
            return false;
        }
        return entity.getIbge() != null && entity.getIbge() > 0;
    }

    @Override
    public void beforeSave(Estado item) {

    }

    @Override
    public void afterSave(Estado savedItem) {

    }

    @Override
    public void beforeDelete(Optional<Estado> item) {

    }

    @Override
    public void afterDelete() {

    }
}
