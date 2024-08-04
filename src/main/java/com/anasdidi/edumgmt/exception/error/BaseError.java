/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.exception.error;

import com.anasdidi.edumgmt.exception.util.ErrorCode;
import java.util.Map;

abstract class BaseError extends RuntimeException {

  public final ErrorCode error;
  public final String logMessage;

  BaseError(ErrorCode error, String logTag, String logMessage) {
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
}
