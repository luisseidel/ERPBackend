package com.seidelsoft.ERPBackend.system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public abstract class BaseService<T> {

    public abstract Optional<T> getById(Long id);

    public abstract List<T> findAll(Sort sort);

    public abstract Page<T> findAllPaged(Pageable pageable);

    public abstract void save(T entity);

    public abstract void delete(Long id);

    public abstract boolean validar(T entity);
}
