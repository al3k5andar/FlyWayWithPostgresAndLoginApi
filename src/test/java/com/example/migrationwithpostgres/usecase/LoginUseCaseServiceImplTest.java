package com.example.migrationwithpostgres.usecase;

import com.example.migrationwithpostgres.data.dao.AppUserDao;
import com.example.migrationwithpostgres.data.model.request.LoginUserRequest;
import com.example.migrationwithpostgres.service.AppUserService;
import com.example.migrationwithpostgres.service.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseServiceImplTest {

    @Mock
    private AppUserService appUserService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private LoginUseCaseServiceImpl loginUseCaseService;

    @Test
    void shouldReturnJwt() {
//        Given
        String token= "token";
        String username= "mark";

        AppUserDao dao= new AppUserDao();
        dao.setId(1L);
        dao.setUsername(username);

        Mockito.when(appUserService.findByUsername(username)).thenReturn(dao);
        Mockito.when(jwtService.createJwt(dao.getId())).thenReturn(token);
//        When
        String jwt= loginUseCaseService.createUserJwt(username);

//        Then
        Assertions.assertEquals(jwt, token);
    }

    @Test
    void shouldCreateOkResponse() {
//        Given
        LoginUserRequest userRequest= new LoginUserRequest("username", "password");

    }
}