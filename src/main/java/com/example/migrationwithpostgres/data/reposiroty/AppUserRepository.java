package com.example.migrationwithpostgres.data.reposiroty;

import com.example.migrationwithpostgres.data.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findById(Long id);
}
