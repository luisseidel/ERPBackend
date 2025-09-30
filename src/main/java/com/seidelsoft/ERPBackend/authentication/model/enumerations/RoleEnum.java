package com.seidelsoft.ERPBackend.authentication.model.enumerations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleEnum implements IEnum {
	ADMIN(1L, "Administrador"),
	USER(2L, "Usuário");

    private final Long value;
	private final String description;

    public RoleEnum valueOf(Long value) {
        for (RoleEnum r : RoleEnum.values()) {
			if (IEnum.valueEquals(r.getValue(), value)) {
				return r;
			}
		}
		return null;
	}

}
