package com.seidelsoft.ERPBackend.system.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public abstract class BaseService<T> {

    public abstract Optional<T> getById(Long id);

    public abstract Page<T> findAll(Pageable pageable);

    public abstract void save(T entity);

    public abstract void delete(Long id);
}
