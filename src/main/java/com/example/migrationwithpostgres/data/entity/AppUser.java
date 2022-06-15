package com.example.migrationwithpostgres.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @JsonIgnore
    @OneToMany(mappedBy = "appUser", fetch = FetchType.EAGER)
    private Set<Role> roles;
}
