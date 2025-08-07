package com.seidelsoft.ERPBackend.system.service;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T> {

    public abstract Optional<T> getById(Long id);

    public abstract List findAll();

    public abstract void save(T entity);

    public abstract void delete(Long id);
}
