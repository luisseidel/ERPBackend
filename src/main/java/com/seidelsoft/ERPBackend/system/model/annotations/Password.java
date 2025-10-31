package com.seidelsoft.ERPBackend.system.model.annotations;

import com.seidelsoft.ERPBackend.system.model.validations.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String message() default "Senha inválida!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
