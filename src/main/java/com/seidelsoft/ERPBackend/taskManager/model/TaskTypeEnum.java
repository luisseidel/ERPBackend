package com.seidelsoft.ERPBackend.taskManager.model;

import com.seidelsoft.ERPBackend.authentication.model.enumerations.IEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaskTypeEnum implements IEnum {

    GERAL(0L, "GERAL"),
    REPORT(1L, "REPORT");

    private final Long value;
    private final String description;

    public static TaskTypeEnum valueOf(Long value) {
        for (TaskTypeEnum t : TaskTypeEnum.values()) {
            if (IEnum.valueEquals(t.getValue(), value)) {
                return t;
            }
        }
        return null;
    }

}
