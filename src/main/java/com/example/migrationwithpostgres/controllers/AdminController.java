package com.example.migrationwithpostgres.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @GetMapping
    public String getHelloAdmin(){
        return "get hello admin";
    }

    @PostMapping
    public String postHelloAdmin(){
        return "post hello admin";
    }
}
