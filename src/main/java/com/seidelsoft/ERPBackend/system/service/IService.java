package com.seidelsoft.ERPBackend.system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface IService<T> {

    Optional<T> getById(Long id);

    List<T> findAll(Sort sort);

    Page<T> findAllPaged(Pageable pageable);

    T save(T entity);

    void delete(Long id);

    boolean validar(T entity);
}
