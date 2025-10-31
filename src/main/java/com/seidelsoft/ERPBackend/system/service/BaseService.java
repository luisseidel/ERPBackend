package com.seidelsoft.ERPBackend.system.service;

import com.seidelsoft.ERPBackend.authorization.entity.User;
import com.seidelsoft.ERPBackend.authorization.repository.UserRepository;
import com.seidelsoft.ERPBackend.system.model.BaseEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public abstract class BaseService<T extends BaseEntity, R extends JpaRepository<T, Long>> implements IService<T> {

    @Autowired
    protected JpaRepository<T, Long> repository;
    @Getter
    @Autowired
    protected R specificRepository;
    @Autowired
    private UserRepository userRepository;

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Override
    public Optional<T> getById(Long id) {
        if (id != null) {
            return repository.findById(id);
        }
        return Optional.empty();
    }

    @Override
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
    public T save(T entity) {
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

    public void beforeSave(T item) {
        StringBuilder msgValidacao = new StringBuilder();

        validarConstraints(item, msgValidacao);

        if (!validar(item, msgValidacao)) {
            throw new IllegalArgumentException("Dados inválidos! " + msgValidacao);
        }
    }

    public void validarConstraints(T entity, StringBuilder sb) {
        Set<ConstraintViolation<T>> violationsPessoa = validator.validate(entity);
        if (!violationsPessoa.isEmpty()) {
            for (ConstraintViolation<T> violation : violationsPessoa) {
                sb.append(violation.getMessage());
                sb.append(System.lineSeparator());
            }
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


    protected String getResourceName() {
        ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<?> entityClass = (Class<?>) superclass.getActualTypeArguments()[0];
        return entityClass.getSimpleName().toUpperCase();
    }

    protected boolean canConsultar(String resourceName) {
        return hasPermission(resourceName, "consultar");
    }

    protected boolean canAdicionar(String resourceName) {
        return hasPermission(resourceName, "adicionar");
    }

    protected boolean canEditar(String resourceName) {
        return hasPermission(resourceName, "editar");
    }

    protected boolean canExcluir(String resourceName) {
        return hasPermission(resourceName, "excluir");
    }

    private boolean hasPermission(String resourceName, String action) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof User user)) return false;

        return user.getRoles().stream()
                .flatMap(role -> role.getRolePermissions().stream())
                .filter(rp -> rp.getPermission().getName().equalsIgnoreCase(resourceName))
                .anyMatch(rp ->
                        switch (action.toLowerCase()) {
                            case "consultar" -> rp.isConsultar();
                            case "adicionar" -> rp.isAdicionar();
                            case "editar" -> rp.isEditar();
                            case "excluir" -> rp.isExcluir();
                            default -> false;
                        });
    }
}
