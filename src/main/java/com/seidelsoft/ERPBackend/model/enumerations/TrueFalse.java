package com.seidelsoft.ERPBackend.model.enumerations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrueFalse implements IEnum {
    FALSE(0L, "False"),
    TRUE(1L, "True");

    private final Long value;
    private final String descricao;

    @Override
    public TrueFalse valueOf(Long value) {
        for (TrueFalse tf : TrueFalse.values()) {
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
