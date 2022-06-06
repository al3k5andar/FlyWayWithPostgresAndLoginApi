package com.example.migrationwithpostgres.service.impl;

import com.example.migrationwithpostgres.data.model.request.LoginUserRequest;
import com.example.migrationwithpostgres.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    @Override
    public boolean isUserAuthenticated(LoginUserRequest userRequest) {
        try {
            Authentication usernamePassToken= new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword());
            Authentication authentication= authenticationManager.authenticate(usernamePassToken);
            if(authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return true;
            }
//            return authenticationManager.authenticate(usernamePassToken).isAuthenticated();
        }
        catch (AuthenticationException e){
            return false;
        }
        return false;
    }
}
