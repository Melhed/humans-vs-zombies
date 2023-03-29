package com.example.backendhvz.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String msg) {
        super("ERROR: " + msg);
    }
}
