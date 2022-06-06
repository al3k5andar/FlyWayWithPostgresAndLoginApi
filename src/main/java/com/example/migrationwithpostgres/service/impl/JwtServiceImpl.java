package com.example.migrationwithpostgres.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.migrationwithpostgres.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value(value = "${secret-value}")
    private String secret;

    @Value(value = "${jwt-token-expiration}")
    private String tokenExpiration;

//    Create new token with secret
    @Override
    public String createJwt(Long userId) {
        Algorithm algorithm= Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(getTokenExpiration())
                .sign(algorithm);
    }

//    Set token expiration
    private Date getTokenExpiration(){
        return new Date(System.currentTimeMillis()+ Long.parseLong(tokenExpiration) * 60 * 1000);
    }
}
