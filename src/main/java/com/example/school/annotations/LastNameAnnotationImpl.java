package com.example.school.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LastNameAnnotationImpl implements ConstraintValidator<LastName,String> {

    @Override
    public boolean isValid(String lastName, ConstraintValidatorContext constraintValidatorContext){

        return lastName.length() > 7;

    }
}