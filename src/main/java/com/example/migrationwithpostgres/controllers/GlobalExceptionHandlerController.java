package com.example.migrationwithpostgres.controllers;

import com.example.migrationwithpostgres.data.model.response.ResponseWrapper;
import com.example.migrationwithpostgres.exceptions.UserNotActivatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalExceptions(Exception exception){
        ResponseWrapper wrapper= ResponseWrapper.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build();

        return ResponseEntity.ok(wrapper);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        List<String> errors= exception.getFieldErrors().stream().map(e -> {
            return e.getDefaultMessage();
        }).toList();
        ResponseWrapper wrapper= ResponseWrapper.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(exception.getParameter().toString())
                .data(errors)
                .build();

        return ResponseEntity.ok(wrapper);
    }

    @ExceptionHandler(UserNotActivatedException.class)
    public ResponseEntity<Object> handleUserNotActivatedException(UserNotActivatedException exception){
        ResponseWrapper wrapper= ResponseWrapper.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message(exception.getMessage())
                .build();

        return ResponseEntity.ok(wrapper);
    }
}
