package com.seidelsoft.ERPBackend.pessoa.controller.api;

import com.seidelsoft.ERPBackend.authentication.controller.api.SecuredController;
import com.seidelsoft.ERPBackend.pessoa.model.Pessoa;
import com.seidelsoft.ERPBackend.pessoa.model.dto.PessoaFindDTO;
import com.seidelsoft.ERPBackend.pessoa.service.PessoaService;
import com.seidelsoft.ERPBackend.system.exception.ValidacaoException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pessoas")
public class PessoaRestController implements SecuredController {

    @Autowired
    private PessoaService service;

    @GetMapping("/id/{id}")
    public ResponseEntity getPessoaById(@PathVariable Long id) {
        Optional<Pessoa> pessoa = service.getPessoaById(id);
        if (pessoa.isPresent()) {
            return ResponseEntity.ok(pessoa);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/dados")
    public ResponseEntity getPessoaBy(@Valid @RequestBody PessoaFindDTO pessoaFind) {
        try {
            List<Pessoa> list = service.getPessoaBy(pessoaFind.getNome(), pessoaFind.getCpf());
            if (list.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(list);

        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Pessoa dto) {
        try {
            Pessoa p = service.create(dto);
            return ResponseEntity.ok(p);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{cpf}")
    public ResponseEntity update(@PathVariable String cpf, @RequestBody Pessoa dto) {
        try {
            Pessoa p = service.update(cpf, dto);
            return ResponseEntity.ok(p);
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
