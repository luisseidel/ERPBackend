package com.seidelsoft.ERPBackend.authorization.controller;

import com.seidelsoft.ERPBackend.authorization.entity.Role;
import com.seidelsoft.ERPBackend.authorization.service.RoleService;
import com.seidelsoft.ERPBackend.system.controller.BaseRestController;
import com.seidelsoft.ERPBackend.system.exception.ValidacaoException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/role")
public class RoleController extends BaseRestController<Role, RoleService> {


    @Override
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Role dto) throws ValidacaoException {
        Role role = getService().save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(role.getId())
                .toUri();

        return ResponseEntity.created(location).body(role);
    }

    @Override
    public ResponseEntity update(@Valid @RequestBody Role dto) throws ValidacaoException {
        return ResponseEntity.ok(getService().save(dto));
    }

}
