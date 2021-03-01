package com.example.school.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CnpAnnotation implements ConstraintValidator<MyCNP,String> {

    @Override
    public boolean isValid(String CNP, ConstraintValidatorContext constraintValidatorContext){

        return (CNP.startsWith("1") || CNP.startsWith("2")) && (CNP.length()==13);

    }

}
