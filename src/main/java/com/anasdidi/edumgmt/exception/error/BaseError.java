/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.exception.error;

public abstract class BaseError extends RuntimeException {

  public final ErrorCode error;
  public final String logMessage;

  public BaseError(ErrorCode error, String logTag, String logMessage) {
    super(error.code);
    this.error = error;
    this.logMessage = "[%s] %s".formatted(logTag, logMessage);
  }

  public enum ErrorCode {
    RECORD_NOT_FOUND("E001"),
    RECORD_NOT_MATCHED("E002");

    public final String code;

    private ErrorCode(String code) {
      this.code = code;
    }
  }
}
