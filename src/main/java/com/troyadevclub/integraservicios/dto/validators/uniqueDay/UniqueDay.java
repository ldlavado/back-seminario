package com.troyadevclub.integraservicios.dto.validators.uniqueDay;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueDayValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueDay {
    String message() default "Days must be unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}