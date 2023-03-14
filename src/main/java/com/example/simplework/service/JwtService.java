package com.example.simplework.service;

import com.example.simplework.entity.model.User;
import com.example.simplework.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class JwtService {
    private static final String HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    @Autowired
    JwtUtil jwtUtil;

    public String generateToken(User user) {
        return jwtUtil.generateToken(user);
    }

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtil.generateToken(userDetails);
    }

    public Claims getClaims(String token) {
        return jwtUtil.extractAllClaims(token);
    }

    public String getUsernameFromToken(String token) {
        return jwtUtil.extractUsername(token);
    }

    public String getEmailFromToken(String token) {
        return jwtUtil.extractEmail(token);
    }

    public boolean validateToken(String token) {
        try {
            return jwtUtil.validateToken(token);
        } catch (Exception e) {
            return false;
        }
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HEADER);
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
