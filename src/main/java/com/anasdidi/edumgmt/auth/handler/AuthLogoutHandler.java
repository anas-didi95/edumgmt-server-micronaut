/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.handler;

import com.anasdidi.edumgmt.auth.service.AuthService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.security.authentication.AuthenticationFailureReason;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.handlers.LogoutHandler;
import jakarta.inject.Singleton;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
class AuthLogoutHandler implements LogoutHandler<HttpRequest<?>, MutableHttpResponse<?>> {

  private static final Logger logger = LoggerFactory.getLogger(AuthLogoutHandler.class);
  private final AuthService authService;

  AuthLogoutHandler(AuthService authService) {
    this.authService = authService;
  }

  @Override
  public MutableHttpResponse<?> logout(HttpRequest<?> request) {
    Principal principal =
        request
            .getUserPrincipal()
            .orElseThrow(
                () -> AuthenticationResponse.exception(AuthenticationFailureReason.UNKNOWN));
    Number totalTokenRevoked = authService.logout(principal);

    logger.debug(
        "[logout] userId={}, totalTokenRevoked={}", principal.getName(), totalTokenRevoked);

    return HttpResponse.ok();
  }
}
