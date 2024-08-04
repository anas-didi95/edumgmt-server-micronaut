/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.exception.error;

import com.anasdidi.edumgmt.exception.util.ErrorCode;

public final class RecordNotMatchedError extends BaseError {

  public RecordNotMatchedError(String methodName, boolean idMatched, boolean versionMatched) {
    super(
        ErrorCode.RECORD_NOT_MATCHED,
        methodName,
        "Record not matched! idMatched=%b, versionMatched=%b".formatted(idMatched, versionMatched));
  }
}
