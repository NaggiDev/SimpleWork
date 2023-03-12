package com.example.simplework.exception;

import com.example.simplework.constant.ResponseStatusCodeEnum;
import com.example.simplework.factory.response.GeneralResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@SuppressWarnings({"squid:S1165", "squid:S1948"})
@Getter
@Setter
public class BusinessException extends RuntimeException {

    private ResponseStatusCodeEnum responseCode;
    private Object returnObject;
    private Map<String, String> errorMessageParam;
    private boolean isSaveHistory;
    private GeneralResponse<Object> fwdGeneralResponse;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(GeneralResponse<Object> fwdGeneralResponse) {
        super();
        this.fwdGeneralResponse = fwdGeneralResponse;
    }

    public BusinessException(String message, ResponseStatusCodeEnum responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

    public BusinessException(ResponseStatusCodeEnum responseCode) {
        super();
        this.responseCode = responseCode;
    }

    public BusinessException(ResponseStatusCodeEnum responseCode, boolean isSaveHistory) {
        super();
        this.responseCode = responseCode;
        this.isSaveHistory = isSaveHistory;
    }

    public BusinessException(
            ResponseStatusCodeEnum responseCode,
            Object returnObject,
            Map<String, String> errorMessageParam) {
        super();
        this.responseCode = responseCode;
        this.returnObject = returnObject;
        this.errorMessageParam = errorMessageParam;
    }

    public BusinessException(ResponseStatusCodeEnum responseCode, Object returnObject) {
        super();
        this.responseCode = responseCode;
        this.returnObject = returnObject;
    }

    public BusinessException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public BusinessException(
            String message, Throwable throwable, ResponseStatusCodeEnum responseCode) {
        super(message, throwable);
        this.responseCode = responseCode;
    }

    public boolean isResponseCodeEquals(ResponseStatusCodeEnum responseCode) {
        return responseCode.getCode().equalsIgnoreCase(this.responseCode.getCode());
    }
}
