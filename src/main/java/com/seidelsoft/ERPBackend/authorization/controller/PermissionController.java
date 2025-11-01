package com.seidelsoft.ERPBackend.authorization.controller;

import com.seidelsoft.ERPBackend.authorization.entity.Permission;
import com.seidelsoft.ERPBackend.authorization.service.PermissionService;
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
@RequestMapping("/api/v1/permission")
public class PermissionController extends BaseRestController<Permission, PermissionService> {

    @Override
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Permission dto) throws ValidacaoException {
        Permission permission = getService().save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(permission.getId())
                .toUri();

        return ResponseEntity.created(location).body(permission);
    }

    @Override
    @PutMapping
    public ResponseEntity update(@Valid @RequestBody Permission dto) throws ValidacaoException {
        return ResponseEntity.ok(getService().save(dto));
    }
}
