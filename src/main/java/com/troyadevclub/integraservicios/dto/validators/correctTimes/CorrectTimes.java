package com.troyadevclub.integraservicios.dto.validators.correctTimes;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CorrectTimesValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CorrectTimes {
    String message() default "Start time must be before or equal to end time";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
