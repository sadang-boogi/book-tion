package com.rebook.common.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private final String code;
    private final String message;

    public BadRequestException(ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}
