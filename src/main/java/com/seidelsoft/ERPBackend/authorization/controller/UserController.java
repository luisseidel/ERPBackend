package com.seidelsoft.ERPBackend.authorization.controller;

import com.seidelsoft.ERPBackend.authorization.entity.User;
import com.seidelsoft.ERPBackend.authorization.service.UserService;
import com.seidelsoft.ERPBackend.system.controller.BaseRestController;
import com.seidelsoft.ERPBackend.system.exception.ValidacaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController extends BaseRestController<User, UserService> {
    @Override
    public ResponseEntity list(int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity get(Long id) {
        return null;
    }

    @Override
    public ResponseEntity create(User dto) throws ValidacaoException {
        return null;
    }

    @Override
    public ResponseEntity update(User dto) throws ValidacaoException {
        return null;
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        return null;
    }
}
