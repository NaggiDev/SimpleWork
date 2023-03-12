package com.example.simplework.exception;

public class EnumInvalidException extends RuntimeException {

  public EnumInvalidException(String message) {
    super(message);
  }

  public EnumInvalidException(String message, Throwable throwable) {
    super(message, throwable);
  }

  public EnumInvalidException(Throwable throwable) {
    super(throwable);
  }
}
