package com.seidelsoft.SpringDemo.controller;

import com.seidelsoft.SpringDemo.model.dto.in.PessoaDTO;
import com.seidelsoft.SpringDemo.model.entity.Pessoa;
import com.seidelsoft.SpringDemo.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pessoas")
public class PessoaController implements SecuredController {

    @Autowired
    private PessoaService service;

    @GetMapping("/{id}")
    public Optional<Pessoa> getPessoa(@PathVariable Long id) {
        return service.getPessoa(id);
    }

    @GetMapping("/{name}")
    public List<Pessoa> getPessoa(@PathVariable String name) {
        return service.getPessoa(name);
    }

    @PostMapping
    public Pessoa create(@RequestBody PessoaDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public Pessoa update(@PathVariable Long id, @RequestBody PessoaDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Deleted!");
    }
}
