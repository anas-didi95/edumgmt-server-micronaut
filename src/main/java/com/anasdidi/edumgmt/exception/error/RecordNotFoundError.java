/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.exception.error;

import com.anasdidi.edumgmt.exception.util.ErrorCode;
import java.util.Map;

public final class RecordNotFoundError extends BaseError {

  public RecordNotFoundError(Map<String, Object> paramMap) {
    super(ErrorCode.RECORD_NOT_FOUND, parseParamMap(paramMap));
  }
}
