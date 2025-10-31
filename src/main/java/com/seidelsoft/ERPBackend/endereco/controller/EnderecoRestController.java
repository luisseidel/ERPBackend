package com.seidelsoft.ERPBackend.endereco.controller;

import com.seidelsoft.ERPBackend.endereco.model.Endereco;
import com.seidelsoft.ERPBackend.endereco.service.EnderecoService;
import com.seidelsoft.ERPBackend.system.controller.BaseRestController;
import com.seidelsoft.ERPBackend.system.exception.ValidacaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/endereco")
public class EnderecoRestController extends BaseRestController<Endereco, EnderecoService> {

    @Override
    public ResponseEntity list(int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity get(Long id) {
        return null;
    }

    @Override
    public ResponseEntity create(Endereco dto) throws ValidacaoException {
        return null;
    }

    @Override
    public ResponseEntity update(Endereco dto) throws ValidacaoException {
        return null;
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        return null;
    }
}
