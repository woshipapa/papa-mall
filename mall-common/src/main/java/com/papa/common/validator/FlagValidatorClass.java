package com.papa.common.validator;

import io.swagger.models.auth.In;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FlagValidatorClass implements ConstraintValidator<FlagValidator,Integer> {

    private String[] values;

    @Override
    public void initialize(FlagValidator constraintAnnotation) {
        values = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Integer o, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = false;
        for(String value : values){
            if(value.equals(String.valueOf(o))){
                isValid = true;
                break;
            }
        }
        return isValid;
    }
}
