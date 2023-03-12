package com.example.simplework.exception;

import com.example.simplework.constant.ResponseStatusCodeEnum;
import com.example.simplework.factory.response.GeneralResponse;
import lombok.Getter;
import lombok.Setter;


@SuppressWarnings({"squid:S1165", "squid:S3740", "squid:S1948"})
@Getter
@Setter
public class ExternalServiceException extends RuntimeException {

  private ResponseStatusCodeEnum code;
  private GeneralResponse response;
  private String message;

  public ExternalServiceException(String message) {
    super(message);
  }

  public ExternalServiceException(ResponseStatusCodeEnum code, GeneralResponse response) {
    super();
    this.code = code;
    this.response = response;
  }
}
