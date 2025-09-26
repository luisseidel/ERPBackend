package com.seidelsoft.ERPBackend.system.exception;

import com.seidelsoft.ERPBackend.system.utils.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GerenciadorErros {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<StandardError> handleRecursoNaoEncontrado(ValidacaoException ex) {

        StandardError se = new StandardError(
            DateUtils.getDateFormatted(),
            HttpStatus.NOT_FOUND.value(),
            "Recurso não encontrado",
            ex.getMessage()
        );

        return new ResponseEntity<>(se, HttpStatus.NOT_FOUND);
    }
}
