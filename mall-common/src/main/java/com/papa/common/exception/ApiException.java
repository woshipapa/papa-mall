package com.papa.common.exception;

import com.papa.common.api.IErrorCode;
import io.swagger.annotations.Api;

public class ApiException extends RuntimeException{

    private IErrorCode iErrorCode;

    public ApiException(IErrorCode errorCode){
        super(errorCode.getMessage());
        this.iErrorCode = errorCode;
    }

    public IErrorCode getiErrorCode() {
        return iErrorCode;
    }

    public ApiException(String message){
        super(message);
    }

    public ApiException(Throwable cause){
        super(cause);
    }

    public ApiException(String message,Throwable cause){
        super(message,cause);
    }
}
