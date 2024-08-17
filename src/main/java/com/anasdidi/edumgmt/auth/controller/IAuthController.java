/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.controller;

import com.anasdidi.edumgmt.auth.dto.LogoutUserDTO;
import io.micronaut.http.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;

@Tag(name = "auth")
public interface IAuthController {

  @Operation(summary = "Logout user and revoked token")
  HttpResponse<LogoutUserDTO> logout(Principal principal);
}
