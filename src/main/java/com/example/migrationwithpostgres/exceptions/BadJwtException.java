package com.example.migrationwithpostgres.exceptions;

public class BadJwtException extends RuntimeException {

    public BadJwtException(String message) {
        super(message);
    }
}
