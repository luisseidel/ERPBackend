package com.seidelsoft.ERPBackend.system.controller;

import com.seidelsoft.ERPBackend.system.exception.ValidacaoException;
import com.seidelsoft.ERPBackend.system.service.BaseService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@Setter
public abstract class BaseRestController<T, K extends BaseService> implements SecuredController {

    @Autowired
    private K service;
    private Page<T> items;

    public abstract ResponseEntity list(@RequestParam(required = false, defaultValue = "0") int page,
                               @RequestParam(required = false, defaultValue = "10") int size);

    public abstract ResponseEntity get(Long id);

    public abstract ResponseEntity create(T dto) throws ValidacaoException;

    public abstract ResponseEntity update(T dto) throws ValidacaoException;

    public abstract ResponseEntity<String> delete(Long id);

}