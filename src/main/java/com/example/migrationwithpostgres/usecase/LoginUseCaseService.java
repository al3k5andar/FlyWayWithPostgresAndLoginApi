package com.example.migrationwithpostgres.usecase;

import com.example.migrationwithpostgres.data.model.request.LoginUserRequest;
import com.example.migrationwithpostgres.data.model.response.ResponseWrapper;
import org.springframework.http.ResponseEntity;

public interface LoginUseCaseService {

    String createUserJwt(String username);

    ResponseEntity<ResponseWrapper> createResponse(LoginUserRequest userRequest);
}
