/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.exception.error;

public final class RecordNotFoundError extends BaseError {

  public RecordNotFoundError(String methodName, String field, Object value) {
    super(
        ErrorCode.RECORD_NOT_FOUND, methodName, "Record not found! %s=%s".formatted(field, value));
  }
}
