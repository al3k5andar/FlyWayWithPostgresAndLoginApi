package com.example.migrationwithpostgres.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @PreAuthorize(value = "hasRole('ADMIN') or hasAuthority('write')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/hello")
    public String hello(){
        return "hello !!!";
    }
}
