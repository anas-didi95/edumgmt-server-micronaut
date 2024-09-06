/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.factory;

import com.anasdidi.edumgmt.auth.service.AuthService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.security.authentication.AuthenticationFailureReason;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.handlers.LogoutHandler;
import jakarta.inject.Singleton;
import java.security.Principal;

@Singleton
class UserLogoutHandler implements LogoutHandler<HttpRequest<?>, MutableHttpResponse<?>> {

  private final AuthService authService;

  UserLogoutHandler(AuthService authService) {
    this.authService = authService;
  }

  @Override
  public MutableHttpResponse<?> logout(HttpRequest<?> request) {
    Principal principal =
        request
            .getUserPrincipal()
            .orElseThrow(
                () -> AuthenticationResponse.exception(AuthenticationFailureReason.UNKNOWN));
    authService.signOut(principal);
    return HttpResponse.ok();
  }
}
