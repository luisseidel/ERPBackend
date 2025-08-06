package com.seidelsoft.ERPBackend.model.enumerations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SimNao implements IEnum {
    NAO(0L, "Não"),
    SIM(1L, "Sim");

    private final Long value;
    private final String descricao;

    @Override
    public SimNao valueOf(Long value) {
        for (SimNao tf : SimNao.values()) {
            if (valueEquals(tf.getValue(), value)) {
                return tf;
            }
        }
        return null;
    }

    @Override
    public boolean valueEquals(Long l1, Long l2) {
        return l1 != null && l2 != null && l1.compareTo(l2) == 0;
    }
}
