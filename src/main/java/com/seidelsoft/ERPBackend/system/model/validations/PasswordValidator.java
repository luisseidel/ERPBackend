package com.seidelsoft.ERPBackend.system.model.validations;

import com.seidelsoft.ERPBackend.system.model.annotations.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&*()\\[\\]{};:/?.>,<=+_\\-§ªº°ç])[A-Za-z\\d!@#$%&*()\\[\\]{};:/?.>,<=+_\\-§ªº°ç]{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    @Override
    public void initialize(Password constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.trim().isBlank()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Senha não pode estar vazia.")
                    .addConstraintViolation();
            return false;
        }

        if (s.trim().length() < 8) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Senha não pode conter menos de 8 caracteres.")
                    .addConstraintViolation();
            return false;
        }

        if (!pattern.matcher(s).matches()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "A senha deve ter no mínimo 8 caracteres e, dentre eles, uma letra maiúscula, uma minúscula, um número e um caractere especial."
            ).addConstraintViolation();
            return false;
        }

        return true;
    }
}
