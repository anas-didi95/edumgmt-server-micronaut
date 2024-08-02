/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.exception.error;

import java.util.Map;

public abstract class BaseError extends RuntimeException {

  public final ErrorCode error;
  public final String logMessage;

  public BaseError(ErrorCode error, String logTag, String logMessage) {
    super(error.code);
    this.error = error;
    this.logMessage = "[%s] %s".formatted(logTag, logMessage);
  }

  protected static String parseParamMap(Map<String, Object> paramMap) {
    return String.join(
        ",",
        paramMap.entrySet().stream()
            .map(param -> "%s=%s".formatted(param.getKey(), param.getValue()))
            .toList());
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
