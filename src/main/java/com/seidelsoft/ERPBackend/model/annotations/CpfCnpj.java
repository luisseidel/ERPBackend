package com.seidelsoft.ERPBackend.model.annotations;

import com.seidelsoft.ERPBackend.model.Validations.CpfCnpjValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CpfCnpjValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CpfCnpj {

    String message() default "CPF / CNPJ inválido!";
}
