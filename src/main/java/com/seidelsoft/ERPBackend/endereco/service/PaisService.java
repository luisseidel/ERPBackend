package com.seidelsoft.ERPBackend.endereco.service;

import com.seidelsoft.ERPBackend.endereco.model.Pais;
import com.seidelsoft.ERPBackend.endereco.repository.PaisRepository;
import com.seidelsoft.ERPBackend.system.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaisService extends BaseService<Pais, PaisRepository> {

    @Override
    public Optional<Pais> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Pais> findAll(Sort sort) {
        if (sort != null) {
            return repository.findAll(sort);
        }
        return null;
    }

    @Override
    public Page<Pais> findAllPaged(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void save(Pais entity) {
        if (!validar(entity)) {
            throw new IllegalArgumentException("Dados inválidos para o pais");
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
    public boolean validar(Pais entity) {
        if (entity.getNome() == null || entity.getNome().isEmpty()) {
            return false;
        }
        if (entity.getSigla() == null || entity.getSigla().isEmpty()) {
            return false;
        }
        return entity.getNomePortugues() != null && !entity.getNomePortugues().isEmpty();
    }
}
