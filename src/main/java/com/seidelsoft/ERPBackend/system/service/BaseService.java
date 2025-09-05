package com.seidelsoft.ERPBackend.system.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T, K> implements IService<T> {

    @Autowired
    protected JpaRepository<T, Long> repository;
    @Getter
    @Autowired
    protected K specificRepository;

    @Override
    public Optional<T> getById(Long id) {
        if (id != null) {
            return repository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public List<T> findAll(Sort sort) {
        if (sort != null) {
            return repository.findAll(sort);
        }
        return null;
    }

    @Override
    public Page<T> findAllPaged(Pageable pageable) {
        if (pageable != null) {
            return repository.findAll(pageable);
        }
        return Page.empty();
    }

    @Override
    public T save(T entity) {
        if (!validar(entity)) {
            throw new IllegalArgumentException("Dados inválidos!");
        }
        beforeSave(entity);
        T saved = repository.save(entity);
        afterSave(saved);
        return saved;
    }

    @Override
    public void delete(Long id) {
        if (id == null || !repository.existsById(id)) {
            throw new IllegalArgumentException("Entidade não encontrada!");
        }
        beforeDelete(repository.findById(id));
        repository.deleteById(id);
        afterDelete();
    }

    public abstract void beforeSave(T item);

    public abstract void afterSave(T savedItem);

    public abstract void beforeDelete(Optional<T> item);

    public abstract void afterDelete();


}
