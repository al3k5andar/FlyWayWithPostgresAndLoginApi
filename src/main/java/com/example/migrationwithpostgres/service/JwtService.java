package com.example.migrationwithpostgres.service;

public interface JwtService {

    public String createJwt(Long userId, String tokenExpiration);
}
