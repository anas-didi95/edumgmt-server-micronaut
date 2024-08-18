/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.common.aspect;

import io.micronaut.runtime.http.scope.RequestScope;
import java.util.UUID;

@RequestScope
public class TraceContext {

  private UUID traceId;
  private String requestParam;

  public UUID getTraceId() {
    return traceId;
  }

  public void setTraceId(UUID traceId) {
    this.traceId = traceId;
  }

  public String getRequestParam() {
    return requestParam;
  }

  public void setRequestParam(String requestParam) {
    this.requestParam = requestParam;
  }
}
