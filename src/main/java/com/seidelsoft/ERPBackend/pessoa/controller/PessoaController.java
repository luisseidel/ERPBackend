package com.seidelsoft.ERPBackend.pessoa.controller;

import com.seidelsoft.ERPBackend.pessoa.model.Pessoa;
import com.seidelsoft.ERPBackend.pessoa.service.PessoaService;
import com.seidelsoft.ERPBackend.system.controller.BaseRestController;
import com.seidelsoft.ERPBackend.system.exception.ValidacaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pessoa")
public class PessoaController extends BaseRestController<Pessoa, PessoaService> {

    @Override
    @GetMapping("/list")
    public ResponseEntity list(@RequestParam(required = false, defaultValue = "0") int page,
                               @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        setItems(getService().findAllPaged(pageable));
        return ResponseEntity.ok(getItems());
    }

    @Override
    @GetMapping("/id/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(getService().getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity create(Pessoa dto) throws ValidacaoException {
        Pessoa pessoa = getService().createUpdate(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pessoa.getId()) // Assuming MyResource has an getId() method
                .toUri();

        return ResponseEntity.created(location).body(pessoa);
    }

    @Override
    @PutMapping
    public ResponseEntity update(Pessoa dto) throws ValidacaoException {
        return ResponseEntity.ok(getService().createUpdate(dto));
    }

    @Override
    @DeleteMapping
    public ResponseEntity<String> delete(Long id) {
        getService().delete(id);
        return ResponseEntity.noContent().build();
    }
}
