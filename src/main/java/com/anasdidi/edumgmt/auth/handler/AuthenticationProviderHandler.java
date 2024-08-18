/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.handler;

import com.anasdidi.edumgmt.auth.entity.User;
import com.anasdidi.edumgmt.auth.repository.UserRepository;
import com.anasdidi.edumgmt.common.factory.CommonProps;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.security.authentication.AuthenticationFailureReason;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.provider.HttpRequestAuthenticationProvider;
import io.micronaut.security.token.Claims;
import jakarta.inject.Singleton;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Singleton
class AuthenticationProviderHandler implements HttpRequestAuthenticationProvider<Object> {

  private static final String ROLE_SWAGGER = "ROLE_SWAGGER";
  private final CommonProps commonProps;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  AuthenticationProviderHandler(
      CommonProps commonProps, UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.commonProps = commonProps;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public @NonNull AuthenticationResponse authenticate(
      @Nullable HttpRequest<Object> requestContext,
      @NonNull AuthenticationRequest<String, String> authRequest) {
    boolean isSwagger =
        requestContext.getHeaders().accept().stream()
            .anyMatch(t -> t.matches(MediaType.TEXT_HTML_TYPE));
    if (isSwagger) {
      return authRequest.getIdentity().equals(commonProps.getBasicAuth().username())
              && authRequest.getSecret().equals(commonProps.getBasicAuth().password())
          ? AuthenticationResponse.success(authRequest.getIdentity(), Arrays.asList(ROLE_SWAGGER))
          : AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH);
    }

    Optional<User> result =
        userRepository.findByUserIdAndIsDeleted(authRequest.getIdentity(), false);
    if (result.isEmpty()) {
      return AuthenticationResponse.failure(AuthenticationFailureReason.USER_NOT_FOUND);
    }

    User user = result.get();
    Map<String, Object> attributeMap = Map.of(Claims.ISSUER, commonProps.getJwt().issuer());
    return passwordEncoder.matches(authRequest.getSecret(), user.getPassword())
        ? AuthenticationResponse.success(authRequest.getIdentity(), user.getRoles(), attributeMap)
        : AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH);
  }
}
