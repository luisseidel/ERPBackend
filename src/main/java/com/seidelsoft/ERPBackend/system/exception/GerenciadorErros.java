package com.seidelsoft.ERPBackend.system.exception;

import com.seidelsoft.ERPBackend.system.utils.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GerenciadorErros {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<StandardError> handleValidacaoException(ValidacaoException ex) {
        StandardError se = new StandardError(
                DateUtils.getDateFormatted(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação",
                ex.getMessage()
        );

        return new ResponseEntity<>(se, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> handleIllegalArgumentException(IllegalArgumentException ex) {
        StandardError se = new StandardError(
                DateUtils.getDateFormatted(),
                HttpStatus.BAD_REQUEST.value(),
                "Argumento inválido",
                ex.getMessage()
        );

        return new ResponseEntity<>(se, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = String.format("O parâmetro '%s' deve ser do tipo %s",
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconhecido"
        );

        StandardError se = new StandardError(
                DateUtils.getDateFormatted(),
                HttpStatus.BAD_REQUEST.value(),
                "Tipo de parâmetro inválido",
                message
        );

        return new ResponseEntity<>(se, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<StandardError> handleNoResourceFound(NoResourceFoundException ex) {
        StandardError se = new StandardError(
                DateUtils.getDateFormatted(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado",
                "A URL solicitada não existe"
        );

        return new ResponseEntity<>(se, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = "Corpo da requisição ausente ou inválido";

        if (ex.getMessage() != null && ex.getMessage().contains("Required request body is missing")) {
            message = "Corpo da requisição é obrigatório";
        }

        StandardError se = new StandardError(
                DateUtils.getDateFormatted(),
                HttpStatus.BAD_REQUEST.value(),
                "Requisição inválida",
                message
        );

        return new ResponseEntity<>(se, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<StandardError> handleRuntimeException(RuntimeException ex) {
        StandardError se = new StandardError(
                DateUtils.getDateFormatted(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno no servidor",
                ex.getMessage()
        );

        return new ResponseEntity<>(se, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleGenericException(Exception ex) {
        StandardError se = new StandardError(
                DateUtils.getDateFormatted(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro inesperado",
                "Ocorreu um erro inesperado no servidor. Por favor, tente novamente."
        );

        return new ResponseEntity<>(se, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}