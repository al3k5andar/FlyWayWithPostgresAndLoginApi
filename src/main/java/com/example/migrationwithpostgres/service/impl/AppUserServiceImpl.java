package com.example.migrationwithpostgres.service.impl;

import com.example.migrationwithpostgres.data.dao.AppUserDao;
import com.example.migrationwithpostgres.data.entity.AppUser;
import com.example.migrationwithpostgres.data.entity.UserStatus;
import com.example.migrationwithpostgres.data.reposiroty.AppUserRepository;
import com.example.migrationwithpostgres.service.AppUserService;
import com.example.migrationwithpostgres.utils.GlobalModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final GlobalModelMapper modelMapper;

    public AppUserServiceImpl(AppUserRepository appUserRepository, GlobalModelMapper modelMapper) {
        this.appUserRepository = appUserRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AppUserDao findByUsername(String username) {

        return modelMapper.appUserToAppUserDto(getUserByUsername(username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser= getUserByUsername(username);
        if(appUser.getUserStatus().equals(UserStatus.ACTIVE)){
            return User.withUsername(appUser.getUsername())
                    .password(appUser.getPassword())
                    .authorities("read") // only for testing purpose
                    .build();
        }
        return null;
    }

    private AppUser getUserByUsername(String username){
        return appUserRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User with username: "+ username + " not found"));
    }
}
