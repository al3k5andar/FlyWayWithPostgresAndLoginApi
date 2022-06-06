package com.example.migrationwithpostgres.service.impl;

import com.example.migrationwithpostgres.data.dao.AppUserDao;
import com.example.migrationwithpostgres.data.entity.AppUser;
import com.example.migrationwithpostgres.data.entity.UserStatus;
import com.example.migrationwithpostgres.data.reposiroty.AppUserRepository;
import com.example.migrationwithpostgres.utils.GlobalModelMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AppUserServiceImplTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private GlobalModelMapper globalModelMapper;

    @InjectMocks
    private AppUserServiceImpl appUserService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldReturnAppUserDtoWhenUsernameIsGiven() {
//        Given
        String username= "mark";
        AppUser appUser= new AppUser();
        appUser.setId(1L);
        appUser.setUsername("mark");

        AppUserDao appUserDao= new AppUserDao();
        appUserDao.setUsername(appUser.getUsername());

        Mockito.when(globalModelMapper.appUserToAppUserDto(appUser)).thenReturn(appUserDao);
        Mockito.when(appUserRepository.findByUsername(username)).thenReturn(Optional.of(appUser));

//        When
        AppUserDao result= appUserService.findByUsername(username);

//        Then
        Assertions.assertEquals(result.getUsername(), appUser.getUsername());
    }

    @Test
    void shouldGetUserByUsernameThrownExcWhenBadUsernameIsGiven() {
//        Given
        String username= "mark";

//        when
        Exception exception= Assertions.assertThrows(UsernameNotFoundException.class, () ->{
            appUserService.findByUsername(username);
        });

//        Then
        Assertions.assertEquals(exception.getMessage(), "User with username: "+ username + " not found");
    }

    @Test
    void loadUserByUsernameShouldReturnUserDetails() {
//        Given
        String username= "mark";
        AppUser appUser= new AppUser();
        appUser.setUsername(username);
        appUser.setPassword("password");
        appUser.setUserStatus(UserStatus.ACTIVE);
        UserDetails details= User.withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .authorities("read")
                .build();

        Mockito.when(appUserRepository.findByUsername(username)).thenReturn(Optional.of(appUser));

//        When
        UserDetails result= appUserService.loadUserByUsername(username);

//        Then
        Assertions.assertEquals(result.getUsername(), details.getUsername());
        Assertions.assertEquals(result.getPassword(), details.getPassword());
    }

    @Test
    void shouldReturnNullWhenUserIsNotActivated() {
//        Given
        String username= "mark";
        AppUser appUser= new AppUser();
        appUser.setUsername(username);
        appUser.setPassword("password");
        appUser.setUserStatus(UserStatus.BLOCK);
        UserDetails details= User.withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .authorities("read")
                .build();

        Mockito.when(appUserRepository.findByUsername(username)).thenReturn(Optional.of(appUser));

//        When
        UserDetails result= appUserService.loadUserByUsername(username);

//        Then
        Assertions.assertNull(result);
    }
}