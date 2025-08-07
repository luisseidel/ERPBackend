package com.seidelsoft.ERPBackend.auth.model.enumerations;

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
    private final String descricao;

    @Override
    public Sexo valueOf(Long value) {
        for (Sexo s : Sexo.values()) {
            if (valueEquals(s.getValue(), value)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public boolean valueEquals(Long l1, Long l2) {
        return l1 != null && l2 != null && l1.compareTo(l2) == 0;
    }
}
