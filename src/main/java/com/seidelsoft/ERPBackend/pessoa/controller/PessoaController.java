package com.seidelsoft.ERPBackend.pessoa.controller;

import com.seidelsoft.ERPBackend.pessoa.model.Pessoa;
import com.seidelsoft.ERPBackend.pessoa.service.PessoaService;
import com.seidelsoft.ERPBackend.system.controller.BaseRestController;
import com.seidelsoft.ERPBackend.system.exception.ValidacaoException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pessoa")
public class PessoaController extends BaseRestController<Pessoa, PessoaService> {

    @Override
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Pessoa dto) throws ValidacaoException {
        Pessoa pessoa = getService().createUpdate(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pessoa.getId())
                .toUri();

        return ResponseEntity.created(location).body(pessoa);
    }

    @Override
    @PutMapping
    public ResponseEntity update(@Valid @RequestBody Pessoa dto) throws ValidacaoException {
        return ResponseEntity.ok(getService().createUpdate(dto));
    }

}
