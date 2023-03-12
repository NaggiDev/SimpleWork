package com.example.simplework.controller.implement;

import com.example.simplework.entity.dto.UserLoginDTO;
import com.example.simplework.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("all")
@RestController
public class AccountController {

    @Autowired
    JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserLoginDTO loginDTO) {

        // authenticate user
//        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUserName());

        // generate token
//        String token = jwtService.generateToken(userDetails.getUsername());

//        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        return null;
    }

}
