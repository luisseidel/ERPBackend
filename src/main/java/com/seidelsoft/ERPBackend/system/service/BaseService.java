package com.seidelsoft.ERPBackend.system.service;

import com.seidelsoft.ERPBackend.authorization.entity.User;
import com.seidelsoft.ERPBackend.authorization.repository.UserRepository;
import com.seidelsoft.ERPBackend.system.model.BaseEntity;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Optional;

public abstract class BaseService<T extends BaseEntity, R extends JpaRepository<T, Long>> implements IService<T> {

    @Autowired
    protected JpaRepository<T, Long> repository;
    @Getter
    @Autowired
    protected R specificRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Cacheable(cacheNames = "#{T(this).getCacheName()}", key = "#id")
    public Optional<T> getById(Long id) {
        if (id != null) {
            return repository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    @Cacheable(cacheNames = "#{T(this).getCacheName()}", key = "'all'")
    public Collection<T> findAll(Sort sort) {
        return (sort != null) ? repository.findAll(sort) : repository.findAll();
    }

    @Override
    public Page<T> findAllPaged(Pageable pageable) {
        if (pageable != null) {
            return repository.findAll(pageable);
        }
        return Page.empty();
    }

    @Override
    @CacheEvict(cacheNames = "#{T(this).getCacheName()}", allEntries = true)
    public T save(T entity) {
        beforeSave(entity);
        T saved = repository.save(entity);
        afterSave(saved);
        return saved;
    }

    @Override
    @CacheEvict(cacheNames = "#{T(this).getCacheName()}", allEntries = true)
    public void delete(Long id) {
        if (id == null || !repository.existsById(id)) {
            throw new IllegalArgumentException("Entidade não encontrada!");
        }
        beforeDelete(repository.findById(id));
        repository.deleteById(id);
        afterDelete();
    }

    public void beforeSave(T item) {
        StringBuilder msgValidacao = new StringBuilder();
        if (!validar(item, msgValidacao)) {
            throw new IllegalArgumentException("Dados inválidos! " + msgValidacao);
        }
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }
        String email = auth.getName(); // ou username
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + email));
    }

    public void afterSave(T savedItem) {}
    public void beforeDelete(Optional<T> item) {}
    public void afterDelete() {}

}
