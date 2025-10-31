package com.seidelsoft.ERPBackend.menu.controller;

import com.seidelsoft.ERPBackend.menu.model.Menu;
import com.seidelsoft.ERPBackend.menu.service.MenuService;
import com.seidelsoft.ERPBackend.system.controller.BaseRestController;
import com.seidelsoft.ERPBackend.system.exception.ValidacaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class MenuController extends BaseRestController<Menu, MenuService> {
    @Override
    public ResponseEntity list(int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity get(Long id) {
        return null;
    }

    @Override
    public ResponseEntity create(Menu dto) throws ValidacaoException {
        return null;
    }

    @Override
    public ResponseEntity update(Menu dto) throws ValidacaoException {
        return null;
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        return null;
    }
}
