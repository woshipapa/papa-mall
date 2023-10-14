package com.papa.common.exception;

import com.papa.common.api.IErrorCode;

/**
 * 断言处理类，用于抛出各种api异常
 */
public class Asserts {

    public static void failed(String message){
        throw new ApiException(message);
    }

    public static void failed(IErrorCode code){
        throw new ApiException(code);
    }
}
