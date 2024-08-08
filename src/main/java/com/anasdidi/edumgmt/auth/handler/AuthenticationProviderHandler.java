/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.handler;

import com.anasdidi.edumgmt.common.factory.CommonProps;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationFailureReason;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.provider.HttpRequestAuthenticationProvider;
import jakarta.inject.Singleton;

@Singleton
class AuthenticationProviderHandler implements HttpRequestAuthenticationProvider<Object> {

  private final CommonProps commonProps;

  AuthenticationProviderHandler(CommonProps commonProps) {
    this.commonProps = commonProps;
  }

  @Override
  public @NonNull AuthenticationResponse authenticate(
      @Nullable HttpRequest<Object> requestContext,
      @NonNull AuthenticationRequest<String, String> authRequest) {
    return authRequest.getIdentity().equals(commonProps.getBasicAuth().username())
            && authRequest.getSecret().equals(commonProps.getBasicAuth().password())
        ? AuthenticationResponse.success(authRequest.getIdentity())
        : AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH);
  }
}
