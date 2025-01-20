package com.jairath.gcs.poc.api.exception.types;


import org.springframework.http.HttpStatus;


public class CustomException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private final HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
