/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.controller;

import com.anasdidi.edumgmt.auth.dto.CreateUserDTO;
import com.anasdidi.edumgmt.auth.dto.DeleteUserDTO;
import com.anasdidi.edumgmt.auth.dto.SearchUserDTO;
import com.anasdidi.edumgmt.auth.dto.UpdateUserDTO;
import com.anasdidi.edumgmt.auth.dto.ViewUserDTO;
import com.anasdidi.edumgmt.common.aspect.TraceLog;
import io.micronaut.http.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "user")
@TraceLog
public interface UserController {

  @Operation(summary = "Add new user record")
  HttpResponse<ViewUserDTO> createUser(CreateUserDTO reqBody);

  @Operation(summary = "Update existing user record")
  HttpResponse<ViewUserDTO> updateUser(UUID id, UpdateUserDTO reqBody);

  @Operation(summary = "Delete existing user record")
  HttpResponse<Void> deleteUser(UUID id, DeleteUserDTO reqBody);

  @Operation(summary = "Search user record")
  HttpResponse<SearchUserDTO> searchUser(
      Optional<String> userId, Optional<String> name, Integer page, Integer size);
}
