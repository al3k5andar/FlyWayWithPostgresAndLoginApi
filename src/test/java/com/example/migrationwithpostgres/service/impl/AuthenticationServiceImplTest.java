package com.example.migrationwithpostgres.service.impl;

import com.example.migrationwithpostgres.data.model.request.LoginUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    void isUserAuthenticatedShouldReturnTrue() {
//        Given
        LoginUserRequest userRequest= new LoginUserRequest("user","password");
        Authentication auth= new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword());

        Mockito.when(authenticationManager.authenticate(auth)).thenReturn(authentication);
        Mockito.when(authentication.isAuthenticated()).thenReturn(true);

//        When
        boolean result= authenticationService.isUserAuthenticated(userRequest);

//        Then
        Assertions.assertTrue(result);
    }

    @Test
    void isUserAuthenticatedShouldReturnFalse() {
//        Given
        LoginUserRequest userRequest= new LoginUserRequest("user","password");
        Authentication auth= new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword());

        Mockito.when(authenticationManager.authenticate(auth)).thenReturn(authentication);

//        When
        boolean result= authenticationService.isUserAuthenticated(userRequest);

//        Then
        Assertions.assertFalse(result);
    }
}