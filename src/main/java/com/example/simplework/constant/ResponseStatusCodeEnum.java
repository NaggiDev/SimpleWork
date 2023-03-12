package com.example.simplework.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ResponseStatusCodeEnum {

    /**
     * response codes should follow the standard: XXXYYYY where - XXX: application shortname - YYYY:
     * numeric code of the error code
     */
    SUCCESS("00", HttpStatus.OK.value()),

    TIMEOUT("32", HttpStatus.OK.value()),
    INTERNAL_SERVER_ERROR("500", HttpStatus.INTERNAL_SERVER_ERROR.value()),
    BAD_REQUEST("400", HttpStatus.BAD_REQUEST.value()),
    FORBIDDEN_INVALID_KEY("403", HttpStatus.FORBIDDEN.value()),
    FORBIDDEN_INVALID_ALG("403", HttpStatus.FORBIDDEN.value()),
    FORBIDDEN_TOKEN_EXPIRE("403", HttpStatus.FORBIDDEN.value()),
    FORBIDDEN_INVALID_EXPIRE("403", HttpStatus.FORBIDDEN.value());

    private final String code;
    private final int httpCode;

    @Override
    public String toString() {
        return "ResponseStatus{" + "code='" + code + '\'' + "httpCode='" + httpCode + '\'' + '}';
    }
}
