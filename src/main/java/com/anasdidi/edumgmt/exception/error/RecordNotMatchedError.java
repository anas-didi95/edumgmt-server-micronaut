/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.exception.error;

import com.anasdidi.edumgmt.exception.util.ErrorCode;

public final class RecordNotMatchedError extends BaseError {

  public RecordNotMatchedError(String methodName, Boolean[] matched, String... fields) {
    super(
        ErrorCode.RECORD_NOT_MATCHED,
        methodName,
        "Record not matched! %s".formatted(parseParamMap(fields, matched)));
  }
}
