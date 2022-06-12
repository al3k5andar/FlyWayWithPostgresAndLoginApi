package com.example.migrationwithpostgres.exceptions;

public class BadUserCredentialsException extends RuntimeException{

    public BadUserCredentialsException(String message) {
        super(message);
    }
}
