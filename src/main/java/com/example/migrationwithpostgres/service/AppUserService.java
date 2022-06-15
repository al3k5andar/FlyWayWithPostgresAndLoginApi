package com.example.migrationwithpostgres.service;

import com.example.migrationwithpostgres.data.dao.AppUserDao;
import com.example.migrationwithpostgres.data.entity.AppUser;
import com.example.migrationwithpostgres.data.model.request.LoginUserRequest;

public interface AppUserService {

    AppUserDao findByUsername(String username);

    Long getRequestedUserId(LoginUserRequest userRequest);

    AppUser getAppUserById(Long id);
}
