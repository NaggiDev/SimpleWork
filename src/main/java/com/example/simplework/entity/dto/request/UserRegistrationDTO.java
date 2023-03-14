package com.example.simplework.entity.dto.request;

import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String email;
    private String userName;
    private String password;
}
