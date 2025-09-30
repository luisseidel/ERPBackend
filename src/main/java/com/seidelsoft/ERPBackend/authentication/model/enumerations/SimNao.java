package com.seidelsoft.ERPBackend.authentication.model.enumerations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SimNao implements IEnum {
    NAO(0L, "Não"),
    SIM(1L, "Sim");

    private final Long value;
    private final String description;

    public SimNao valueOf(Long value) {
        for (SimNao tf : SimNao.values()) {
            if (IEnum.valueEquals(tf.getValue(), value)) {
                return tf;
            }
        }
        return null;
    }
}
