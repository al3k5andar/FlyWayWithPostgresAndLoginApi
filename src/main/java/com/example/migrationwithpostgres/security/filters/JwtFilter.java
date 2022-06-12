package com.example.migrationwithpostgres.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.migrationwithpostgres.data.dao.security.AppUserDetailsImpl;
import com.example.migrationwithpostgres.data.entity.AppUser;
import com.example.migrationwithpostgres.service.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Value("${secret-value}")
    private String secret;

    private final AppUserService appUserService;

    public JwtFilter(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt= request.getHeader(HttpHeaders.AUTHORIZATION);

        if(jwt != null){
            try {
                Algorithm algorithm= Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));
                JWTVerifier jwtVerifier= JWT.require(algorithm).build();
                Date expirationDate= jwtVerifier.verify(jwt).getExpiresAt();
                if(expirationDate.before(Date.from(Instant.now())))
                    throw new RuntimeException("Token is expired!");
                else {
                    Long userId= Long.parseLong(jwtVerifier.verify(jwt).getSubject());
                    AppUser appUser= appUserService.getAppUserById(userId);
                    AppUserDetailsImpl appUserDetails= new AppUserDetailsImpl(appUser);
                    Authentication authentication= new UsernamePasswordAuthenticationToken(appUserDetails.getUsername(), null, appUserDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request,response);
                }
            }
            catch (Exception e){
                log.error("Exception: {}", e.getMessage());
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/api/v1/login");
    }
}
