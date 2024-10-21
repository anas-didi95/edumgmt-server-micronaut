/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.controller;

import com.anasdidi.edumgmt.auth.dto.CreateUserDTO;
import com.anasdidi.edumgmt.auth.dto.DeleteUserDTO;
import com.anasdidi.edumgmt.auth.dto.SearchUserDTO;
import com.anasdidi.edumgmt.auth.dto.UpdateUserDTO;
import com.anasdidi.edumgmt.auth.dto.ViewUserDTO;
import com.anasdidi.edumgmt.auth.service.UserService;
import com.anasdidi.edumgmt.common.util.CommonConstants;
import com.anasdidi.edumgmt.common.util.CommonUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import java.util.Optional;
import java.util.UUID;

@Controller("/user")
class UserControllerImpl implements UserController {

  private final UserService userService;

  UserControllerImpl(UserService userService) {
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
  @Put("/{id}")
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

  @Override
  @Get
  public HttpResponse<SearchUserDTO> searchUser(
      @QueryValue Optional<String> userId,
      @QueryValue Optional<String> name,
      @QueryValue(defaultValue = CommonConstants.SEARCH_DEFAULT_PAGE) Integer page,
      @QueryValue(defaultValue = CommonConstants.SEARCH_DEFAULT_SIZE) Integer size) {
    SearchUserDTO resBody =
        userService.searchUser(
            userId.orElse(null), name.orElse(null), CommonUtils.preparePagable(page, size));
    return HttpResponse.ok(resBody);
  }

  @Override
  @Get("/{id}")
  public HttpResponse<ViewUserDTO> viewUser(UUID id) {
    ViewUserDTO resBody = userService.viewUser(id);
    return HttpResponse.ok(resBody);
  }
}
