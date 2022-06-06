package com.example.migrationwithpostgres.service;

import com.example.migrationwithpostgres.data.model.request.LoginUserRequest;

public interface AuthenticationService {

    boolean isUserAuthenticated(LoginUserRequest userRequest);
}
