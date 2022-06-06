package com.example.migrationwithpostgres.service;

import com.example.migrationwithpostgres.data.dao.AppUserDao;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {

    AppUserDao findByUsername(String username);
}
