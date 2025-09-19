package com.seidelsoft.ERPBackend.system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.Optional;

public interface IService<T> {

    Optional<T> getById(Long id);

    Collection<T> findAll(Sort sort);

    Page<T> findAllPaged(Pageable pageable);

    T save(T entity);

    void delete(Long id);

    default boolean validar(T entity, StringBuilder msgValidacao) {
        return true;
    }
}
