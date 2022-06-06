package com.example.migrationwithpostgres.usecase;

import com.example.migrationwithpostgres.data.dao.AppUserDao;
import com.example.migrationwithpostgres.data.model.request.LoginUserRequest;
import com.example.migrationwithpostgres.data.model.response.LoginUserResponse;
import com.example.migrationwithpostgres.data.model.response.ResponseWrapper;
import com.example.migrationwithpostgres.service.AppUserService;
import com.example.migrationwithpostgres.service.AuthenticationService;
import com.example.migrationwithpostgres.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginUseCaseServiceImpl implements LoginUseCaseService{

    private final AppUserService appUserService;
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public String createUserJwt(String username){

        AppUserDao userDao= appUserService.findByUsername(username);
        return jwtService.createJwt(userDao.getId());
    }

    public ResponseEntity<ResponseWrapper> createResponse(LoginUserRequest userRequest){
        if(authenticationService.isUserAuthenticated(userRequest)){
            ResponseWrapper wrapper= ResponseWrapper.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Authentication is successful.")
                    .data( new LoginUserResponse(createUserJwt(userRequest.getUsername())))
                    .build();
            return ResponseEntity.ok(wrapper);
        }
        ResponseWrapper wrapper= ResponseWrapper.builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .message("Access denied.")
                .build();
        return ResponseEntity.ok(wrapper);
    }
}
