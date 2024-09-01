/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.controller;

import com.anasdidi.edumgmt.auth.client.AuthClient;
import com.anasdidi.edumgmt.auth.dto.RefreshAccessDTO;
import com.anasdidi.edumgmt.auth.dto.SignInDTO;
import com.anasdidi.edumgmt.auth.dto.SignOutDTO;
import com.anasdidi.edumgmt.auth.service.AuthService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.endpoints.TokenRefreshRequest;
import io.micronaut.security.token.render.AccessRefreshToken;
import io.micronaut.security.token.render.BearerAccessRefreshToken;
import java.security.Principal;

@Controller("/auth")
class AuthControllerImpl implements AuthController {

  private final AuthService authService;
  private final AuthClient authClient;

  AuthControllerImpl(AuthService authService, AuthClient authClient) {
    this.authService = authService;
    this.authClient = authClient;
  }

  @Override
  @Post("/signOut")
  public HttpResponse<SignOutDTO> signOut(Principal principal) {
    SignOutDTO resBody = authService.signOut(principal);
    return HttpResponse.ok(resBody);
  }

  @Override
  @Post("/signIn")
  @ExecuteOn(TaskExecutors.BLOCKING)
  public HttpResponse<BearerAccessRefreshToken> signIn(@Body SignInDTO reqBody) {
    UsernamePasswordCredentials credentials =
        new UsernamePasswordCredentials(reqBody.userId(), reqBody.password());
    return authClient.login(credentials);
  }

  @Override
  @Post("/refreshAccess")
  @ExecuteOn(TaskExecutors.BLOCKING)
  public HttpResponse<AccessRefreshToken> refreshAccess(@Body RefreshAccessDTO reqBody) {
    TokenRefreshRequest request =
        new TokenRefreshRequest(
            TokenRefreshRequest.GRANT_TYPE_REFRESH_TOKEN, reqBody.refreshToken());
    return authClient.refresh(request);
  }
}
