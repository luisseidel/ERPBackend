package com.seidelsoft.ERPBackend.controller;

import com.seidelsoft.ERPBackend.model.dto.in.PessoaDTO;
import com.seidelsoft.ERPBackend.model.entity.Pessoa;
import com.seidelsoft.ERPBackend.model.exception.ValidacaoException;
import com.seidelsoft.ERPBackend.service.PessoaService;
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
    public ResponseEntity getPessoaById(@PathVariable Long id) {
        Optional<Pessoa> pessoa = service.getPessoaById(id);
        if (pessoa.isPresent()) {
            return ResponseEntity.ok(pessoa);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/")
    public ResponseEntity getPessoaBy(@RequestParam String nome, @RequestParam String cpf) {
        try {
            List<Pessoa> list = service.getPessoaBy(nome, cpf);
            if (list.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(list);

        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity create(@RequestBody PessoaDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody PessoaDTO dto) {
        try {
            return service.update(id, dto);
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Deleted!");
    }
}
