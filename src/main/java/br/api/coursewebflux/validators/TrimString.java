package br.api.coursewebflux.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = TrimStringValidator.class)
@Target(value = {ElementType.FIELD})
@Retention(RUNTIME)
public @interface TrimString {

    String message() default "field cannot have spaces at the beginning or end";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}

