package com.jairath.gcs.poc.controller.exceptionhandler;


import com.jairath.gcs.poc.api.exception.responses.ErrorResponse;
import com.jairath.gcs.poc.api.exception.types.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
@Slf4j
public class GCSCRUDPOCExceptionHandler {


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex, WebRequest request) {

        log.error("CustomException occurred: {}", String.valueOf(ex));

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity
                .status(ex.getHttpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptionClass(Exception ex, WebRequest request) {

        log.error("Exception occurred: {}", String.valueOf(ex));

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
}
