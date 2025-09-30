package com.seidelsoft.ERPBackend.authentication.model.enumerations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sexo implements IEnum {
	NENHUM(0L, "Nenhuma"),
    MASCULINO(1L, "Masculino"),
    FEMININO(2L, "Feminino")
    ;

    private final Long value;
    private final String description;

    public Sexo valueOf(Long value) {
        for (Sexo s : Sexo.values()) {
            if (IEnum.valueEquals(s.getValue(), value)) {
                return s;
            }
        }
        return null;
    }

}
