package com.seidelsoft.ERPBackend.system.exception;

public record StandardError(
        String formattedDate,
        Integer status,
        String error,
        String message
) { }
