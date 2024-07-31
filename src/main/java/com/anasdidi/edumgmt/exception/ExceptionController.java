/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.exception;

import com.anasdidi.edumgmt.exception.error.RecordNotFoundError;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/exception")
@Requires(env = "test-exception")
public class ExceptionController {

  @Get("/recordNotFoundError")
  HttpResponse<Void> recordNotFoundError() {
    throw new RecordNotFoundError();
  }
}
