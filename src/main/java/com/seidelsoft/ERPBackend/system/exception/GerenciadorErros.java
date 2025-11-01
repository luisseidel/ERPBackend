package com.seidelsoft.ERPBackend.system.exception;

import com.seidelsoft.ERPBackend.system.utils.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }
}
