package com.example.migrationwithpostgres.usecase;

import com.example.migrationwithpostgres.data.model.request.LoginUserRequest;
import com.example.migrationwithpostgres.data.model.response.LoginUserResponse;
import com.example.migrationwithpostgres.service.AppUserService;
import com.example.migrationwithpostgres.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginUseCaseService implements UseCaseService<LoginUserResponse, LoginUserRequest> {

    private final AppUserService appUserService;
    private final JwtService jwtService;

    @Value("${jwt.expiration.access-token}")
    private String accessTokenDuration;

    @Value("${jwt.expiration.refresh-token}")
    private String refreshTokenDuration;

    public LoginUserResponse execute(LoginUserRequest userRequest){
        Long userId= appUserService.getRequestedUserId(userRequest);

        return LoginUserResponse.builder()
                .id(userId)
                .accessToken(jwtService.createJwt(userId, accessTokenDuration))
                .refreshToken(jwtService.createJwt(userId, refreshTokenDuration))
                .build();
    }
}
