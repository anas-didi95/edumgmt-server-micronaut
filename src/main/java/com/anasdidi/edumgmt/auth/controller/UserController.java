/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.controller;

import com.anasdidi.edumgmt.auth.dto.CreateUserDTO;
import com.anasdidi.edumgmt.auth.dto.DeleteUserDTO;
import com.anasdidi.edumgmt.auth.dto.UpdateUserDTO;
import com.anasdidi.edumgmt.auth.dto.ViewUserDTO;
import com.anasdidi.edumgmt.auth.service.IUserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import java.util.UUID;

@Controller("/user")
class UserController implements IUserController {

  private final IUserService userService;

  UserController(IUserService userService) {
    this.userService = userService;
  }

  @Override
  @Post
  public HttpResponse<ViewUserDTO> createUser(@Body CreateUserDTO reqBody) {
    UUID id = userService.createUser(reqBody);
    ViewUserDTO resBody = userService.viewUser(id);
    return HttpResponse.created(resBody);
  }

  @Override
  @Post("/{id}")
  public HttpResponse<ViewUserDTO> updateUser(@PathVariable UUID id, @Body UpdateUserDTO reqBody) {
    UUID updateId = userService.updateUser(id, reqBody);
    ViewUserDTO resBody = userService.viewUser(updateId);
    return HttpResponse.ok(resBody);
  }

  @Override
  @Delete("/{id}")
  public HttpResponse<Void> deleteUser(@PathVariable UUID id, @Body DeleteUserDTO reqBody) {
    userService.deleteUser(id, reqBody);
    return HttpResponse.noContent();
  }
}
