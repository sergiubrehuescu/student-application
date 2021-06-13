package com.example.school.annotations.PhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberAnnotationImpl implements ConstraintValidator<PhoneNumber,String> {

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext){

        //return phoneNumber.startsWith("07") && phoneNumber.length()==10;
        return true;

    }
}