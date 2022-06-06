package com.example.migrationwithpostgres.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class GlobalPasswordEncoder {

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public PasswordEncoder passwordEncoder(int strength){
        return new BCryptPasswordEncoder(strength);
    }
}
