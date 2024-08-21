/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.exception.error;

import com.anasdidi.edumgmt.exception.util.ErrorCode;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseError extends RuntimeException {

  public final ErrorCode error;
  public final String variable;

  BaseError(ErrorCode error, String variable) {
    super(error.code);
    this.error = error;
    this.variable = variable;
  }

  protected static String parseParamMap(Map<String, Object> paramMap) {
    return String.join(
        ",",
        paramMap.entrySet().stream()
            .map(param -> "%s=%s".formatted(param.getKey(), param.getValue()))
            .toList());
  }

  protected static String parseParamMap(String[] fields, Object[] values) {
    Map<String, Object> paramMap = new HashMap<>();
    for (int i = 0; i < fields.length; i++) {
      paramMap.put(fields[i] + "Matched", values[i]);
    }
    return parseParamMap(paramMap);
  }
}
