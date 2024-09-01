/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.controller;

import com.anasdidi.edumgmt.auth.dto.SignOutDTO;
import com.anasdidi.edumgmt.auth.service.AuthService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import java.security.Principal;

@Controller("/auth")
class AuthControllerImpl implements AuthController {

  private final AuthService authService;

  AuthControllerImpl(AuthService authService) {
    this.authService = authService;
  }

  @Override
  @Post("/signOut")
  public HttpResponse<SignOutDTO> signOut(Principal principal) {
    SignOutDTO resBody = authService.signOut(principal);
    return HttpResponse.ok(resBody);
  }
}
