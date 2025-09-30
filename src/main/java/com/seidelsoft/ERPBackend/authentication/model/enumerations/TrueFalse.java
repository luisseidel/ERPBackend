package com.seidelsoft.ERPBackend.authentication.model.enumerations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrueFalse implements IEnum {
    FALSE(0L, "False"),
    TRUE(1L, "True");

    private final Long value;
    private final String description;

    public static TrueFalse valueOf(Long value) {
        for (TrueFalse tf : TrueFalse.values()) {
            if (IEnum.valueEquals(tf.getValue(), value)) {
                return tf;
            }
        }
        return null;
    }

}
