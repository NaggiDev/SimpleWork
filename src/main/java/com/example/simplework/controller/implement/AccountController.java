package com.example.simplework.controller.implement;

import com.example.simplework.controller.AccountInterface;
import com.example.simplework.entity.dto.request.UserLoginDTO;
import com.example.simplework.entity.dto.request.UserRegistrationDTO;
import com.example.simplework.entity.dto.response.RegistrationResponseDTO;
import com.example.simplework.entity.model.User;
import com.example.simplework.factory.response.JwtResponse;
import com.example.simplework.factory.response.ResponseFactory;
import com.example.simplework.service.JwtService;
import com.example.simplework.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("all")
@RestController
public class AccountController implements AccountInterface {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    ResponseFactory responseFactory;

    @Autowired
    AuthenticationManager authenticationManager;


    @Override
    public ResponseEntity<?> userRegistration(UserRegistrationDTO userRegistrationDTO) {
        if (userAccountService.isExistUser(userRegistrationDTO.getEmail())) {
            return responseFactory.success(new RegistrationResponseDTO("Email address already in use"));
        }

        User user = new User();
        user.setEmail(userRegistrationDTO.getEmail());
        user.setUserName(userRegistrationDTO.getUserName());
        user.setPassword(new BCryptPasswordEncoder().encode(userRegistrationDTO.getPassword()));

        userAccountService.createNewUser(user);

        String token = jwtService.generateToken(user);
        return responseFactory.success(new RegistrationResponseDTO("Register Successful"));
    }


    public ResponseEntity<?> userLogin(@RequestBody UserLoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateToken(authentication);

        return responseFactory.success(new JwtResponse(jwt));
    }

}
