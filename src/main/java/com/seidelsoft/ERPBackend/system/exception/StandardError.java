package com.seidelsoft.ERPBackend.system.exception;

public record StandardError(
        Long timestamp,
        Integer status,
        String error,
        String message
) { }
