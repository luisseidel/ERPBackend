package com.seidelsoft.ERPBackend.endereco.service;

import com.seidelsoft.ERPBackend.endereco.model.Estado;
import com.seidelsoft.ERPBackend.endereco.repository.EstadoRepository;
import com.seidelsoft.ERPBackend.system.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoService extends BaseService<Estado, EstadoRepository> {

    @Override
    public Optional<Estado> getById(Long id) {
        if (id != null) {
            return repository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public List<Estado> findAll(Sort sort) {
        if (sort != null) {
            return repository.findAll(sort);
        }
        return null;
    }

    @Override
    public Page<Estado> findAllPaged(Pageable pageable) {
        if (pageable != null) {
            return repository.findAll(pageable);
        }
        return Page.empty();
    }

    @Override
    public void save(Estado entity) {
        if (!validar(entity)) {
            throw new IllegalArgumentException("Dados inválidos para o estado");
        }
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if (id == null || !repository.existsById(id)) {
            throw new IllegalArgumentException("Estado não encontrado");
        }
        repository.deleteById(id);
    }

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
    
}
