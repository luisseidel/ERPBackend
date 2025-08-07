package com.seidelsoft.ERPBackend.auth.model.enumerations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role implements IEnum {
	ADMIN(1L, "Administrador"),
	USER(2L, "Usuário");

    private final Long value;
	private final String descricao;

	@Override
	public Role valueOf(Long value) {
		for (Role r : Role.values()) {
			if (valueEquals(r.getValue(), value)) {
				return r;
			}
		}
		return null;
	}

	@Override
	public boolean valueEquals(Long l1, Long l2) {
		return l1 != null && l2 != null && l1.compareTo(l2) == 0;
	}
}
