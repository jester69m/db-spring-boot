package com.springboot.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UkrainePhoneNumberValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UkrainePhoneNumber {
    String message() default "Invalid phone number(It should be +380*********)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
