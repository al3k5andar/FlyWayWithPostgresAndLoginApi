package com.example.migrationwithpostgres.data.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginUserResponse {

    private Long id;
    private String accessToken;
    private String refreshToken;
}
