package com.example.migrationwithpostgres.controllers;

import com.example.migrationwithpostgres.data.model.response.ErrorResponse;
import com.example.migrationwithpostgres.exceptions.BadUserCredentialsException;
import com.example.migrationwithpostgres.exceptions.ResourceNotFoundException;
import com.example.migrationwithpostgres.exceptions.UserNotActivatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        ErrorResponse response= new ErrorResponse("Bad validation");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UserNotActivatedException.class)
    public ResponseEntity<ErrorResponse> handleUserNotActivatedException(UserNotActivatedException exception){
        ErrorResponse response= new ErrorResponse(exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(BadUserCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadUserCredentialsException(BadUserCredentialsException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(exception.getMessage()));
    }
}
