package com.seidelsoft.ERPBackend.menu.controller;

import com.seidelsoft.ERPBackend.menu.model.Menu;
import com.seidelsoft.ERPBackend.menu.service.MenuService;
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
@RequestMapping("/api/v1/menu")
public class MenuController extends BaseRestController<Menu, MenuService> {

    @Override
    @GetMapping("/list")
    public ResponseEntity list(int page, int size) {
        return ResponseEntity.ok(getService().findRootMenusWithChildrenByUser());
    }

    @Override
    public ResponseEntity create(@Valid @RequestBody Menu dto) throws ValidacaoException {
        Menu menu = getService().save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(menu.getId())
                .toUri();

        return ResponseEntity.created(location).body(menu);
    }

    @Override
    @PutMapping
    public ResponseEntity update(@Valid @RequestBody Menu dto) throws ValidacaoException {
        return ResponseEntity.ok(getService().save(dto));
    }


}
