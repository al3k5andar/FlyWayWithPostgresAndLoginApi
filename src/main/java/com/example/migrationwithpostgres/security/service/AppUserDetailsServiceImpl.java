package com.example.migrationwithpostgres.security.service;

import com.example.migrationwithpostgres.data.dao.security.AppUserDetailsImpl;
import com.example.migrationwithpostgres.data.entity.AppUser;
import com.example.migrationwithpostgres.data.reposiroty.AppUserRepository;
import com.example.migrationwithpostgres.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsServiceImpl implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser= appUserRepository.findByUsername(username)
                .orElseThrow(()-> new ResourceNotFoundException("User with username: "+ username + " not found"));

        return new AppUserDetailsImpl(appUser);
    }
}
