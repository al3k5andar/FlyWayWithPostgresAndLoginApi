package com.example.migrationwithpostgres.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.migrationwithpostgres.data.dao.security.AppUserDetailsImpl;
import com.example.migrationwithpostgres.data.entity.AppUser;
import com.example.migrationwithpostgres.data.model.response.ErrorResponse;
import com.example.migrationwithpostgres.exceptions.BadJwtException;
import com.example.migrationwithpostgres.exceptions.TokenNotFoundException;
import com.example.migrationwithpostgres.service.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final String BEARER= "Bearer ";
    @Value("${secret-value}")
    private String secret;

    private final AppUserService appUserService;

    public JwtFilter(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token= request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            if(token != null && token.startsWith(BEARER)){
                String jwt= token.substring(BEARER.length());

                SecurityContextHolder.getContext().setAuthentication(getAuthenticatedUser(jwt));
                filterChain.doFilter(request,response);
            }
            else {
                throw new TokenNotFoundException("Token is missing.");
            }
        } catch (Exception e){
            createErrorResponse(response, e);
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/api/v1/login");
    }

    private Authentication getAuthenticatedUser(String jwt){
        try {
            Algorithm algorithm= Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));
            JWTVerifier jwtVerifier= JWT.require(algorithm).build();
            Long userId= Long.parseLong(jwtVerifier.verify(jwt).getSubject());
            AppUser appUser= appUserService.getAppUserById(userId);
            AppUserDetailsImpl appUserDetails= new AppUserDetailsImpl(appUser);
            for(GrantedAuthority r: appUserDetails.getAuthorities()){
                log.warn("ROLE: "+ r.getAuthority());
            }
            return new UsernamePasswordAuthenticationToken(appUserDetails.getUsername(), null, appUserDetails.getAuthorities());
        }
        catch (Exception e){
            throw new BadJwtException("Bad jwt.");
        }
    }

    private void createErrorResponse(HttpServletResponse response, Exception e) throws IOException {
        log.error("Exception: {}", e.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse errorResponse= new ErrorResponse(e.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }
}
