package com.example.migrationwithpostgres.controllers;

import com.example.migrationwithpostgres.data.model.request.LoginUserRequest;
import com.example.migrationwithpostgres.data.model.response.ResponseWrapper;
import com.example.migrationwithpostgres.usecase.LoginUseCaseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final LoginUseCaseServiceImpl loginUseCaseService;


    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper> login(@Valid @RequestBody LoginUserRequest loginUserRequest){

        return loginUseCaseService.createResponse(loginUserRequest);
    }
}
