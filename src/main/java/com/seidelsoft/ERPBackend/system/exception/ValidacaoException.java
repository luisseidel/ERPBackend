package com.seidelsoft.ERPBackend.system.exception;

public class ValidacaoException extends Exception {

    public ValidacaoException(String message) {
        super(message);
    }

    public ValidacaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
