/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.controller;

import com.anasdidi.edumgmt.auth.dto.LogoutUserDTO;
import com.anasdidi.edumgmt.auth.service.IAuthService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import java.security.Principal;

@Controller("/auth")
class AuthController implements IAuthController {

  private final IAuthService authService;

  AuthController(IAuthService authService) {
    this.authService = authService;
  }

  @Override
  @Post("/logout")
  public HttpResponse<LogoutUserDTO> logout(Principal principal) {
    LogoutUserDTO resBody = authService.logout(principal);
    return HttpResponse.ok(resBody);
  }
}
