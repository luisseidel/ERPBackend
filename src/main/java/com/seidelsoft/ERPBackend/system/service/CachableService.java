package com.seidelsoft.ERPBackend.system.service;

import com.seidelsoft.ERPBackend.system.model.BaseEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Optional;

public class CachableService<T extends BaseEntity, R extends JpaRepository<T, Long>> extends BaseService<T, R> {


    @Override
    @Cacheable(cacheNames = "#{#root.target.getEntityCacheName()}", key = "#id")
    public Optional<T> getById(Long id) {
        return super.getById(id);
    }

    @Cacheable(cacheNames = "#{#root.target.getEntityCacheName()}", key = "'all'")
    public Collection<T> findAll(Sort sort) {
        return super.findAll(sort);
    }

    @CacheEvict(cacheNames = "#{#root.target.getEntityCacheName()}", allEntries = true)
    public T save(T entity) {
        return super.save(entity);
    }

    @CacheEvict(cacheNames = "#{#root.target.getEntityCacheName()}", allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }

    /**
     * Gera o nome do cache baseado na entidade T automaticamente
     * Ex: User -> "userCache", Product -> "productCache"
     */
    public static String getEntityCacheName() {
        try {
            // Pegamos o tipo genérico da subclasse em tempo de execução
            ParameterizedType superclass = (ParameterizedType) Class.forName(
                    Thread.currentThread().getStackTrace()[2].getClassName()
            ).getGenericSuperclass();

            Class<?> entityClass = (Class<?>) superclass.getActualTypeArguments()[0];
            String name = entityClass.getSimpleName();
            return name.substring(0,1).toLowerCase() + name.substring(1) + "Cache";
        } catch (Exception e) {
            // fallback
            return "defaultCache";
        }
    }

}
