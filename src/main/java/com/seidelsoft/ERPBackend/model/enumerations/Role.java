package com.seidelsoft.ERPBackend.model.enumerations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	ADMIN(1L, "Administrador"),
	USER(2L, "Usuário");

    private final Long value;
	private final String descricao;
}
