package com.codingshuttle.springbootwebtutorial.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint( validatedBy = {PrimeNumberValidator.class})
public @interface PrimeNumberAnnotation {

    String message() default "please enter the prime number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
