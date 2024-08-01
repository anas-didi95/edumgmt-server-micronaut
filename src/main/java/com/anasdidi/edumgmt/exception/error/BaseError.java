/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.exception.error;

public abstract class BaseError extends RuntimeException {

  protected ErrorCode errorCode;

  public abstract ErrorCode getError();

  public enum ErrorCode {
    RECORD_NOT_FOUND("E001", "Record not found!");

    public final String code;
    public final String message;

    private ErrorCode(String code, String message) {
      this.code = code;
      this.message = message;
    }
  }
}
