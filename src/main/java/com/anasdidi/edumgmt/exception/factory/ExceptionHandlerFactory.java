/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.exception.factory;

import com.anasdidi.edumgmt.common.aspect.TraceContext;
import com.anasdidi.edumgmt.exception.error.BaseError;
import com.anasdidi.edumgmt.exception.error.RecordNotFoundError;
import com.anasdidi.edumgmt.exception.error.RecordNotMatchedError;
import com.anasdidi.edumgmt.exception.util.ErrorCode;
import io.micronaut.context.LocalizedMessageSource;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.http.server.exceptions.response.ErrorContext;
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Factory
class ExceptionHandlerFactory {

  private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerFactory.class);
  private final ErrorResponseProcessor<?> processor;
  private final LocalizedMessageSource messageSource;
  private final TraceContext traceContext;

  @Inject
  ExceptionHandlerFactory(
      ErrorResponseProcessor<?> processor,
      LocalizedMessageSource messageSource,
      TraceContext traceContext) {
    this.processor = processor;
    this.messageSource = messageSource;
    this.traceContext = traceContext;
  }

  @Singleton
  @Requires(classes = {RecordNotFoundError.class, ExceptionHandler.class})
  ExceptionHandler<RecordNotFoundError, HttpResponse<?>> recordNotFoundError() {
    return (request, exception) -> {
      HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
      String message = getMessage(exception.error, httpStatus, traceContext.getTraceId());
      List<String> errorMessages = prepareErrorMessage(exception.error, message);
      return prepareResponse(
          "recordNotFoundError", exception, message, request, errorMessages, httpStatus);
    };
  }

  @Singleton
  @Requires(classes = {RecordNotMatchedError.class, ExceptionHandler.class})
  ExceptionHandler<RecordNotMatchedError, HttpResponse<?>> recordNotMatchedError() {
    return (request, exception) -> {
      HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
      String message = getMessage(exception.error, httpStatus, traceContext.getTraceId());
      List<String> errorMessages = prepareErrorMessage(exception.error, message);
      return prepareResponse(
          "recordNotMatchedError", exception, message, request, errorMessages, httpStatus);
    };
  }

  private String getMessage(ErrorCode error, HttpStatus httpStatus, Object... variables) {
    return messageSource.getMessageOrDefault(
        "error." + error.code, httpStatus.getReason(), variables);
  }

  private List<String> prepareErrorMessage(ErrorCode error, String message) {
    return Arrays.asList(error.code, message, traceContext.getTraceId().toString());
  }

  private HttpResponse<?> prepareResponse(
      String logTag,
      BaseError exception,
      String message,
      HttpRequest<?> request,
      List<String> errorMessages,
      HttpStatus httpStatus) {
    logger.debug("[{}] errorCode={}, message={}", logTag, exception.error.code, message);
    logger.debug(
        "[{}] classMethod={}, variable={}",
        logTag,
        traceContext.getClassMethod(),
        exception.variable);
    logger.debug(
        "[{}] controller={}, controllerParam={}",
        logTag,
        traceContext.getController(),
        traceContext.getControllerParam());

    return processor.processResponse(
        ErrorContext.builder(request).cause(exception).errorMessages(errorMessages).build(),
        HttpResponse.status(httpStatus, message));
  }
}
