/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.mapper;

import com.anasdidi.edumgmt.auth.dto.CreateUserDTO;
import com.anasdidi.edumgmt.auth.dto.ViewUserDTO;
import com.anasdidi.edumgmt.auth.entity.User;
import io.micronaut.context.annotation.Mapper;
import jakarta.inject.Singleton;

@Singleton
public interface UserMapper {

  @Mapper
  User toEntity(CreateUserDTO dto);

  @Mapper
  ViewUserDTO toDTO(User entity);
}
