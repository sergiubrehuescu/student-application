package com.example.school.annotations.LastName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LastNameAnnotationImpl implements ConstraintValidator<LastName,String> {

    @Override
    public boolean isValid(String lastName, ConstraintValidatorContext constraintValidatorContext){

        //return lastName.length() > 7;
        return true;

    }
}