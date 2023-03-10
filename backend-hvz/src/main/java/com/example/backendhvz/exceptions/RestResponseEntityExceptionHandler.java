package com.example.backendhvz.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleBadRequest(
            BadRequestException ex, WebRequest req) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex,
                bodyOfResponse,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                req);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFound(
            NotFoundException ex, WebRequest req) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex,
                bodyOfResponse,
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                req);
    }
    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<Object> handleForbidden(
            BadRequestException ex, WebRequest req) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex,
                bodyOfResponse,
                new HttpHeaders(),
                HttpStatus.FORBIDDEN, req);
    }
}
