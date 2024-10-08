/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.exception.controller;

import com.anasdidi.edumgmt.common.aspect.TraceLog;
import com.anasdidi.edumgmt.exception.error.RecordNotFoundError;
import com.anasdidi.edumgmt.exception.error.RecordNotMatchedError;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.Map;

@Controller("/exception")
@Requires(env = "test")
@Hidden
@TraceLog
class ExceptionController {

  @Get("/recordNotFoundError")
  HttpResponse<Void> recordNotFoundError() {
    throw new RecordNotFoundError(Map.of());
  }

  @Get("/recordNotMatchedError")
  HttpResponse<Void> recordNotMatchedError() {
    throw new RecordNotMatchedError(new Boolean[] {false}, "test");
  }
}
