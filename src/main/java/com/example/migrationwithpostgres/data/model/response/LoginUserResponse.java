package com.example.migrationwithpostgres.data.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginUserResponse {

    private String accessToken;
}
