/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.service;

import com.anasdidi.edumgmt.auth.dto.CreateUserDTO;
import com.anasdidi.edumgmt.auth.dto.UpdateUserDTO;
import com.anasdidi.edumgmt.auth.dto.ViewUserDTO;
import com.anasdidi.edumgmt.auth.entity.User;
import com.anasdidi.edumgmt.auth.mapper.UserMapper;
import com.anasdidi.edumgmt.auth.repository.UserRepository;
import com.anasdidi.edumgmt.exception.error.RecordNotFoundError;
import com.anasdidi.edumgmt.exception.error.RecordNotMatchedError;
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
  public UUID createUser(CreateUserDTO dto) {
    User entity = userMapper.toEntity(dto);
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

  @Override
  public UUID updateUser(UUID id, UpdateUserDTO dto) {
    Optional<User> result = userRepository.findById(id);
    if (result.isEmpty()) {
      RecordNotFoundError error = new RecordNotFoundError("updateUser", Map.of("id", id));
      logger.debug(error.logMessage);
      throw error;
    }

    User entity = result.get();
    boolean[] matched = checkRecordMatched(entity, dto.id(), dto.version());
    if (!matched[0] || !matched[1]) {
      RecordNotMatchedError error = new RecordNotMatchedError("updateUser", matched[0], matched[1]);
      logger.debug(error.logMessage);
      throw error;
    }

    entity.setName(dto.name());
    return userRepository.save(entity).getId();
  }

  private boolean[] checkRecordMatched(User entity, UUID id, Integer version) {
    boolean idMatched = entity.getId().compareTo(id) == 0;
    boolean versionMatched = entity.getVersion().compareTo(version) == 0;
    return new boolean[] {idMatched, versionMatched};
  }
}
