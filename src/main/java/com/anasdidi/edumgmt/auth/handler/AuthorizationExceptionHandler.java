/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.handler;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor;
import io.micronaut.security.authentication.AuthorizationException;
import io.micronaut.security.authentication.DefaultAuthorizationExceptionHandler;
import io.micronaut.security.config.RedirectConfiguration;
import io.micronaut.security.config.RedirectService;
import io.micronaut.security.errors.PriorToLoginPersistence;
import jakarta.inject.Singleton;

@Singleton
@Replaces(DefaultAuthorizationExceptionHandler.class)
class AuthorizationExceptionHandler extends DefaultAuthorizationExceptionHandler {

  public AuthorizationExceptionHandler(
      ErrorResponseProcessor<?> errorResponseProcessor,
      RedirectConfiguration redirectConfiguration,
      RedirectService redirectService,
      @Nullable PriorToLoginPersistence<?, ?> priorToLoginPersistence) {
    super(errorResponseProcessor, redirectConfiguration, redirectService, priorToLoginPersistence);
  }

  @Override
  protected MutableHttpResponse<?> httpResponseWithStatus(
      HttpRequest<?> request, AuthorizationException e) {
    if (e.isForbidden()) {
      return HttpResponse.status(HttpStatus.FORBIDDEN);
    }
    return HttpResponse.status(HttpStatus.UNAUTHORIZED)
        .header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"edumgmt\"");
  }
}
