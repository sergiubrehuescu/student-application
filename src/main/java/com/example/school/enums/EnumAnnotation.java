package com.example.school.enums;

//Marker Annotation
//Single Value Annotation
//Multi Value Annotation
//Meta Annotation

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
//@Repeatable(List.class)
@Documented
@Constraint(validatedBy = { })
public @interface EnumAnnotation {

    String message() default "Must be MALE or FEMALE only";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    Gender genderType(); //email




}
