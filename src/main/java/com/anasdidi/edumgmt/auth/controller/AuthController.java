/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.controller;

import com.anasdidi.edumgmt.auth.dto.SignOutDTO;
import com.anasdidi.edumgmt.common.aspect.TraceLog;
import io.micronaut.http.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;

@Tag(name = "auth")
@TraceLog
public interface AuthController {

  @Operation(summary = "Sign out user and revoked token")
  HttpResponse<SignOutDTO> signOut(Principal principal);
}
