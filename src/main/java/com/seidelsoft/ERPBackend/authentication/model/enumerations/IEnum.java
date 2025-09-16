package com.seidelsoft.ERPBackend.authentication.model.enumerations;

public interface IEnum {
    Object valueOf(Long value);
    boolean valueEquals(Long l1, Long l2);
}
