package com.seidelsoft.ERPBackend.authorization.controller;

import com.seidelsoft.ERPBackend.authorization.entity.User;
import com.seidelsoft.ERPBackend.authorization.service.UserService;
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
@RequestMapping("/api/v1/user")
public class UserController extends BaseRestController<User, UserService> {

    @Override
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody User dto) throws ValidacaoException {
        User user = getService().save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(location).body(user);
    }

    @Override
    public ResponseEntity update(@Valid @RequestBody User dto) throws ValidacaoException {
        return ResponseEntity.ok(getService().save(dto));
    }

}
