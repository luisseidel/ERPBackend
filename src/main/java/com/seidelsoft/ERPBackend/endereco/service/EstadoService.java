package com.seidelsoft.ERPBackend.endereco.service;

import com.seidelsoft.ERPBackend.endereco.model.Estado;
import com.seidelsoft.ERPBackend.endereco.repository.EstadoRepository;
import com.seidelsoft.ERPBackend.system.service.CachableService;
import org.springframework.stereotype.Service;

@Service
public class EstadoService extends CachableService<Estado, EstadoRepository> {

    @Override
    public boolean validar(Estado entity, StringBuilder msgValidacao) {
        if (entity.getNome() == null || entity.getNome().isEmpty()) {
            return false;
        }
        if (entity.getUf() == null || entity.getUf().isEmpty()) {
            return false;
        }
        if (entity.getIbge() == null || entity.getIbge() <= 0) {
            return false;
        }
        return msgValidacao.isEmpty();
    }

}
