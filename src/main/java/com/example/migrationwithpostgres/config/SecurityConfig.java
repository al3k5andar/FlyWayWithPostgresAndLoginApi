package com.example.migrationwithpostgres.config;

import com.example.migrationwithpostgres.controllers.handler.CustomAccessDeniedHandler;
import com.example.migrationwithpostgres.security.filters.JwtFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests(c -> {
            c.antMatchers("/api/v1/login").permitAll();
            c.antMatchers("/api/v1/admin/**").hasRole("ADMIN");
            c.antMatchers("/api/v1/users/**").hasAnyRole("ADMIN","USER");
        });
        http.addFilterBefore(jwtFilter, BasicAuthenticationFilter.class);
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());

    }
}
