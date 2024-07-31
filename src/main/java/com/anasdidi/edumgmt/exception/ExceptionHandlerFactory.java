/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.exception;

import com.anasdidi.edumgmt.exception.error.RecordNotFoundError;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.http.server.exceptions.response.ErrorContext;
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Factory
public class ExceptionHandlerFactory {

  private final ErrorResponseProcessor<?> processor;

  @Inject
  public ExceptionHandlerFactory(ErrorResponseProcessor<?> processor) {
    this.processor = processor;
  }

  @Singleton
  @Requires(classes = {RecordNotFoundError.class, ExceptionHandler.class})
  ExceptionHandler<RecordNotFoundError, HttpResponse<?>> recordNotFoundError() {
    return (request, exception) -> {
      String message = "Record not found!";
      return processor.processResponse(
          ErrorContext.builder(request).cause(exception).errorMessage(message).build(),
          HttpResponse.status(HttpStatus.BAD_REQUEST, message));
    };
  }
}
