/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.exception.error;

public abstract class BaseError extends RuntimeException {

  protected ErrorCode errorCode;

  public abstract ErrorCode getError();

  public enum ErrorCode {
    RECORD_NOT_FOUND("E001");

    public final String code;

    private ErrorCode(String code) {
      this.code = code;
    }
  }
}
