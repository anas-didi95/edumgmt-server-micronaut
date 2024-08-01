/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.exception.error;

public class RecordNotFoundError extends BaseError {

  @Override
  public ErrorCode getError() {
    return ErrorCode.RECORD_NOT_FOUND;
  }
}
