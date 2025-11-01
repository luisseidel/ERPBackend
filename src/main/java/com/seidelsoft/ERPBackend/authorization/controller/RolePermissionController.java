package com.seidelsoft.ERPBackend.authorization.controller;

import com.seidelsoft.ERPBackend.authorization.entity.RolePermission;
import com.seidelsoft.ERPBackend.authorization.service.RolePermissionService;
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
@RequestMapping("/api/v1/rolepermission")
public class RolePermissionController extends BaseRestController<RolePermission, RolePermissionService> {

    @Override
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody RolePermission dto) throws ValidacaoException {
        RolePermission rolePermission = getService().save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(rolePermission.getId())
                .toUri();

        return ResponseEntity.created(location).body(rolePermission);
    }

    @Override
    @PutMapping
    public ResponseEntity update(@Valid @RequestBody RolePermission dto) throws ValidacaoException {
        return ResponseEntity.ok(getService().save(dto));
    }

}
