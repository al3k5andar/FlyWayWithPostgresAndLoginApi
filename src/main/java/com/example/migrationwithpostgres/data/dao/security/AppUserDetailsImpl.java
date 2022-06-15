package com.example.migrationwithpostgres.data.dao.security;

import com.example.migrationwithpostgres.data.entity.AppUser;
import com.example.migrationwithpostgres.data.entity.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class AppUserDetailsImpl implements UserDetails {

    private final AppUser appUser;

    public AppUserDetailsImpl(AppUser appUser) {
        this.appUser = appUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return appUser.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+ role.getName().toUpperCase()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return appUser.getPassword();
    }

    @Override
    public String getUsername() {
        return appUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return appUser.getUserStatus().equals(UserStatus.ACTIVE);
    }
}
