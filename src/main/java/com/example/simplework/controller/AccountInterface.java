package com.example.simplework.controller;

import com.example.simplework.entity.dto.request.UserLoginDTO;
import com.example.simplework.entity.dto.request.UserRegistrationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("${application-context-name}/v1/account/")
public interface AccountInterface {

    @PostMapping("/register")
    public ResponseEntity<?> userRegistration(UserRegistrationDTO userRegistrationDTO);

    @PostMapping("/authenticate")
    public ResponseEntity<?> userLogin(UserLoginDTO loginDTO);
}
