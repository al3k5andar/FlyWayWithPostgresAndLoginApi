package com.example.migrationwithpostgres.data.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserRequest {

    @NotNull
    @Size(min = 2, message = "Username must be greater than 2 characters")
    private String username;

    @NotNull
    @Size(min = 5, max = 16, message = "Password must be between 5 and 16 characters long")
    private String password;
}
