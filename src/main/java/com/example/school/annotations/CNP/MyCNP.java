package com.example.school.annotations.CNP;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {MyCNPAnnotationImpl.class})
public @interface MyCNP {

    String message() default "Trebuie sa inceapa cu 1 sau 2 si exact 13 caractere";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
