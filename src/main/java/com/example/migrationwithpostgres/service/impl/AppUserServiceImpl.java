package com.example.migrationwithpostgres.service.impl;

import com.example.migrationwithpostgres.data.dao.AppUserDao;
import com.example.migrationwithpostgres.data.dao.security.AppUserDetailsImpl;
import com.example.migrationwithpostgres.data.entity.AppUser;
import com.example.migrationwithpostgres.data.entity.UserStatus;
import com.example.migrationwithpostgres.data.model.request.LoginUserRequest;
import com.example.migrationwithpostgres.data.reposiroty.AppUserRepository;
import com.example.migrationwithpostgres.exceptions.BadUserCredentialsException;
import com.example.migrationwithpostgres.exceptions.ResourceNotFoundException;
import com.example.migrationwithpostgres.exceptions.UserNotActivatedException;
import com.example.migrationwithpostgres.service.AppUserService;
import com.example.migrationwithpostgres.utils.GlobalModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final GlobalModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public AppUserServiceImpl(AppUserRepository appUserRepository, GlobalModelMapper modelMapper,
                              PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUserDao findByUsername(String username) {

        return modelMapper.appUserToAppUserDto(getUserByUsername(username));
    }

    @Override
    public AppUser getAppUserById(Long id) {
        AppUser appUser= appUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: "+ id + " is not found."));
        return appUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser= getUserByUsername(username);

        return new AppUserDetailsImpl(appUser);
    }

    @Override
    public Long getRequestedUserId(LoginUserRequest userRequest){
        AppUser appUser= getUserByUsername(userRequest.getUsername());
        if(!appUser.getUserStatus().equals(UserStatus.ACTIVE)){
            throw new UserNotActivatedException("User with username: "+ userRequest.getUsername()+ " is not activated");
        }
        boolean isValid= checkUserCredentials(userRequest.getPassword(), appUser.getPassword());
        if(!isValid){
            throw new BadUserCredentialsException("Bad credentials !!!");
        }

        return appUser.getId();
    }

    private AppUser getUserByUsername(String username){
        return appUserRepository.findByUsername(username)
                .orElseThrow(()-> new ResourceNotFoundException("User with username: "+ username + " not found"));
    }

    private boolean checkUserCredentials(String rawPass, String encodedPass){
        return passwordEncoder.matches(rawPass, encodedPass);
    }
}
