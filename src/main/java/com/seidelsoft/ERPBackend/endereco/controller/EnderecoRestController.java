package com.seidelsoft.ERPBackend.endereco.controller;

import com.seidelsoft.ERPBackend.endereco.model.Endereco;
import com.seidelsoft.ERPBackend.endereco.service.EnderecoService;
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
@RequestMapping("/api/v1/endereco")
public class EnderecoRestController extends BaseRestController<Endereco, EnderecoService> {

    @Override
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Endereco dto) throws ValidacaoException {
        Endereco endereco = getService().save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(endereco.getId())
                .toUri();

        return ResponseEntity.created(location).body(endereco);
    }

    @Override
    @PutMapping
    public ResponseEntity update(@Valid @RequestBody Endereco dto) throws ValidacaoException {
        return ResponseEntity.ok(getService().save(dto));
    }

}
