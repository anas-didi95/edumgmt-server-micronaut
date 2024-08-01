/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.exception;

import com.anasdidi.edumgmt.exception.error.BaseError.ErrorCode;
import com.anasdidi.edumgmt.exception.error.RecordNotFoundError;
import io.micronaut.context.LocalizedMessageSource;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.http.server.exceptions.response.ErrorContext;
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Arrays;
import java.util.List;

@Factory
public class ExceptionHandlerFactory {

  private final ErrorResponseProcessor<?> processor;
  private final LocalizedMessageSource messageSource;

  @Inject
  public ExceptionHandlerFactory(
      ErrorResponseProcessor<?> processor, LocalizedMessageSource messageSource) {
    this.processor = processor;
    this.messageSource = messageSource;
  }

  @Singleton
  @Requires(classes = {RecordNotFoundError.class, ExceptionHandler.class})
  ExceptionHandler<RecordNotFoundError, HttpResponse<?>> recordNotFoundError() {
    return (request, exception) -> {
      HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
      ErrorCode error = exception.error;
      String message = getMessage(error, httpStatus);
      List<String> messageList = Arrays.asList(error.code, message);

      return processor.processResponse(
          ErrorContext.builder(request).cause(exception).errorMessages(messageList).build(),
          HttpResponse.status(HttpStatus.BAD_REQUEST, message));
    };
  }

  private String getMessage(ErrorCode error, HttpStatus httpStatus) {
    return messageSource.getMessageOrDefault("error." + error.code, httpStatus.getReason());
  }
}
