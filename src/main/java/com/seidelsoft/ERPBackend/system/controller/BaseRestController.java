package com.seidelsoft.ERPBackend.system.controller;

import com.seidelsoft.ERPBackend.system.exception.ValidacaoException;
import com.seidelsoft.ERPBackend.system.service.BaseService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Getter
@Setter
@RestController
public abstract class BaseRestController<T, K extends BaseService> implements SecuredController {

    @Autowired
    private K service;
    private Page<T> items;

    @GetMapping("/list")
    public ResponseEntity list(@RequestParam(required = false, defaultValue = "0") int page,
                               @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        setItems(getService().findAllPaged(pageable));
        return ResponseEntity.ok(getItems());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(getService().getById(id));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        getService().delete(id);
        return ResponseEntity.noContent().build();
    }

    public abstract ResponseEntity create(@Valid @RequestBody T dto) throws ValidacaoException;

    public abstract ResponseEntity update(@Valid @RequestBody T dto) throws ValidacaoException;

}