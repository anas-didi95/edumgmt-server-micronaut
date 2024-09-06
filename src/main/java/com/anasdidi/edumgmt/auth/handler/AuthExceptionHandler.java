/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.handler;

import com.anasdidi.edumgmt.common.factory.CommonProps;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor;
import io.micronaut.security.authentication.AuthorizationException;
import io.micronaut.security.authentication.DefaultAuthorizationExceptionHandler;
import io.micronaut.security.config.RedirectConfiguration;
import io.micronaut.security.config.RedirectService;
import io.micronaut.security.errors.PriorToLoginPersistence;
import jakarta.inject.Singleton;
import java.util.Base64;

@Singleton
@Replaces(DefaultAuthorizationExceptionHandler.class)
class AuthExceptionHandler extends DefaultAuthorizationExceptionHandler {

  private final String basicAuthHash;

  AuthExceptionHandler(
      ErrorResponseProcessor<?> errorResponseProcessor,
      RedirectConfiguration redirectConfiguration,
      RedirectService redirectService,
      @Nullable PriorToLoginPersistence<?, ?> priorToLoginPersistence,
      CommonProps commonProps) {
    super(errorResponseProcessor, redirectConfiguration, redirectService, priorToLoginPersistence);
    this.basicAuthHash =
        Base64.getEncoder()
            .encodeToString(
                (commonProps.getBasicAuth().username()
                        + ":"
                        + commonProps.getBasicAuth().password())
                    .getBytes());
  }

  @Override
  protected MutableHttpResponse<?> httpResponseWithStatus(
      HttpRequest<?> request, AuthorizationException e) {
    if (e.isForbidden()) {
      return HttpResponse.status(HttpStatus.FORBIDDEN);
    }

    boolean isSwagger =
        request.getHeaders().accept().stream().anyMatch(t -> t.matches(MediaType.TEXT_HTML_TYPE));
    if (isSwagger) {
      return HttpResponse.status(HttpStatus.UNAUTHORIZED)
          .header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"edumgmt\"");
    }
    return super.httpResponseWithStatus(request, e);
  }

  @Override
  protected String getRedirectUri(HttpRequest<?> request, AuthorizationException exception) {
    String[] authArr = request.getHeaders().getAuthorization().orElse("").split(" ");
    if (authArr.length == 2 && authArr[0].equals("Basic") && authArr[1].equals(basicAuthHash)) {
      return "/edumgmt/swagger-ui/index.html";
    }
    return super.getRedirectUri(request, exception);
  }
}
