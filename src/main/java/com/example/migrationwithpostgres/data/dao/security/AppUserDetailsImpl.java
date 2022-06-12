package com.example.migrationwithpostgres.data.dao.security;

import com.example.migrationwithpostgres.data.entity.AppUser;
import com.example.migrationwithpostgres.data.entity.Role;
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
        Collection<SimpleGrantedAuthority> authorities= appUser.getRoles()
                .stream()
                .flatMap(role -> {
                    return role.getPermissions().stream();
                })
                .map(permission -> {
                    return new SimpleGrantedAuthority(permission.getName());
                }).collect(Collectors.toList());
        for(Role r: appUser.getRoles()){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+ r.getName().toUpperCase()));
        }
        return authorities;
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
