/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.common.aspect;

import io.micronaut.runtime.http.scope.RequestScope;
import java.util.UUID;

@RequestScope
public class TraceContext {

  private UUID traceId;
  private String controller;
  private String controllerParam;
  private String classMethod;

  public UUID getTraceId() {
    return traceId;
  }

  public void setTraceId(UUID traceId) {
    this.traceId = traceId;
  }

  public String getController() {
    return controller;
  }

  public void setController(String controller) {
    this.controller = controller;
  }

  public String getControllerParam() {
    return controllerParam;
  }

  public void setControllerParam(String controllerParam) {
    this.controllerParam = controllerParam;
  }

  public String getClassMethod() {
    return classMethod;
  }

  public void setClassMethod(String classMethod) {
    this.classMethod = classMethod;
  }
}
