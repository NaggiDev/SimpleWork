package com.example.simplework.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponseDTO implements BaseResponseDTO {

    private String message;
}
