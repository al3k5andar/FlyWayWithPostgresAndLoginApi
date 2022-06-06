package com.example.migrationwithpostgres.data.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDao {

    private Long id;
    private String username;
    private String email;
}
