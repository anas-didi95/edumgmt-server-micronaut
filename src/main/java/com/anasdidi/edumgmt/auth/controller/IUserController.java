/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.controller;

import com.anasdidi.edumgmt.auth.dto.CreateUserDTO;
import com.anasdidi.edumgmt.auth.dto.UpdateUserDTO;
import com.anasdidi.edumgmt.auth.dto.ViewUserDTO;
import io.micronaut.http.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;

@Tag(name = "user")
public interface IUserController {

  @Operation(summary = "Add new user record")
  HttpResponse<ViewUserDTO> createUser(CreateUserDTO reqBody);

  @Operation(summary = "Update existing user record")
  HttpResponse<ViewUserDTO> updateUser(UUID id, UpdateUserDTO reqBody);
}
