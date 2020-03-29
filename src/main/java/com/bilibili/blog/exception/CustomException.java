package com.bilibili.blog.exception;

public class CustomException extends RuntimeException {
    private String message;
    private Integer code;

    public CustomException(CustomExceptionCode customExceptionCode) {
        this.message = customExceptionCode.getMessage();
        this.code = customExceptionCode.getCode();
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
