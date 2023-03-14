package com.example.simplework.factory.response;

import com.example.simplework.entity.dto.response.BaseResponseDTO;
import lombok.Data;

@Data
public class JwtResponse implements BaseResponseDTO {
    private final String token;
}
