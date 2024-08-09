/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.service;

import com.anasdidi.edumgmt.auth.dto.CreateUserDTO;
import com.anasdidi.edumgmt.auth.dto.ViewUserDTO;
import com.anasdidi.edumgmt.auth.entity.User;
import com.anasdidi.edumgmt.auth.mapper.UserMapper;
import com.anasdidi.edumgmt.auth.repository.UserRepository;
import com.anasdidi.edumgmt.exception.error.RecordNotFoundError;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Transactional
class UserService implements IUserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);
  private final UserMapper userMapper;
  private final UserRepository userRepository;

  UserService(UserMapper userMapper, UserRepository userRepository) {
    this.userMapper = userMapper;
    this.userRepository = userRepository;
  }

  @Override
  public UUID createUser(CreateUserDTO reqBody) {
    User entity = userMapper.toEntity(reqBody);
    return userRepository.save(entity).getId();
  }

  @Override
  public ViewUserDTO viewUser(UUID id) {
    Optional<User> result = userRepository.findById(id);
    if (result.isEmpty()) {
      RecordNotFoundError error = new RecordNotFoundError("viewUser", Map.of("id", id));
      logger.debug(error.logMessage);
      throw error;
    }
    return userMapper.toDTO(result.get());
  }
}
