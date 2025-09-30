package com.seidelsoft.ERPBackend.authentication.model.enumerations;

public interface IEnum {

    Long getValue();
    String getDescription();

    static boolean valueEquals(Long l1, Long l2) {
        return l1 != null && l2 != null && l1.compareTo(l2) == 0;
    }

}
