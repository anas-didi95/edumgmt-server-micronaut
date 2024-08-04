/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.exception.util;

public enum ErrorCode {
  RECORD_NOT_FOUND("E001"),
  RECORD_NOT_MATCHED("E002");

  public final String code;

  private ErrorCode(String code) {
    this.code = code;
  }
}
