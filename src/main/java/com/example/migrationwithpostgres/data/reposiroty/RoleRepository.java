package com.example.migrationwithpostgres.data.reposiroty;

import com.example.migrationwithpostgres.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
