package com.papa.common.api;

public enum ResultCode implements IErrorCode{
    SUCCESS(200,"操作成功"),

    FAILED(500,"操作失败"),

    FORBIDDEN(401,"用户权限不足"),

    UNAUTHORIZED(403,"暂未登陆或者token已失效"),

    VALIDATE_FAILED(404,"参数校验错误");


    private long code;
    private String msg;
     ResultCode(long code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }
}
