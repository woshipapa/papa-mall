package com.papa.common.exception;

import com.papa.common.api.CommonResult;
import org.apache.ibatis.binding.BindingException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public CommonResult handleValidException(MethodArgumentNotValidException ex){
        BindingResult result = ex.getBindingResult();
        String message = null;
        if(result.hasErrors()){
            FieldError fieldError = result.getFieldError();
            if(fieldError!=null){
                message = fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validate_failed(message);
    }


    @ExceptionHandler(value = BindingException.class)
    @ResponseBody
    public CommonResult handleValidException(BindException exception){
        BindingResult result = exception.getBindingResult();
        String message = null;
        if(result.hasErrors()){
            FieldError fieldError = result.getFieldError();
            if(fieldError!=null){
                message = fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validate_failed(message);


    }
}
