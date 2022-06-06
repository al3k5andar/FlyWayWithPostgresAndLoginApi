package com.example.migrationwithpostgres.utils;

import com.example.migrationwithpostgres.data.dao.AppUserDao;
import com.example.migrationwithpostgres.data.entity.AppUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

public class GlobalModelMapper {

    public AppUserDao appUserToAppUserDto(AppUser source){
        return getModelMapper().map(source, AppUserDao.class);
    }

    private ModelMapper getModelMapper(){
        ModelMapper modelMapper= new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }
}
