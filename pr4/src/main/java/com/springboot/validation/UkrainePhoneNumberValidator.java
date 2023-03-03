package com.springboot.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UkrainePhoneNumberValidator implements ConstraintValidator<UkrainePhoneNumber, String> {
    @Override
    public void initialize(UkrainePhoneNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.startsWith("+380") && value.length() == 13 && value.matches("\\+[0-9]{12}");
    }
}
