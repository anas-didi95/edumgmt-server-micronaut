/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.exception.error;

import java.util.Map;

public final class RecordNotFoundError extends BaseError {

  public RecordNotFoundError(String methodName, Map<String, Object> paramMap) {
    super(
        ErrorCode.RECORD_NOT_FOUND,
        methodName,
        "Record not found! %s".formatted(parseParamMap(paramMap)));
  }
}
