/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.service;

import com.anasdidi.edumgmt.auth.dto.CreateUserDTO;
import com.anasdidi.edumgmt.auth.dto.DeleteUserDTO;
import com.anasdidi.edumgmt.auth.dto.UpdateUserDTO;
import com.anasdidi.edumgmt.auth.dto.ViewUserDTO;
import com.anasdidi.edumgmt.auth.entity.User;
import com.anasdidi.edumgmt.auth.mapper.UserMapper;
import com.anasdidi.edumgmt.auth.repository.UserRepository;
import com.anasdidi.edumgmt.common.service.BaseService;
import com.anasdidi.edumgmt.exception.error.RecordNotFoundError;
import com.anasdidi.edumgmt.exception.error.RecordNotMatchedError;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;

@Singleton
@Transactional
class UserServiceImpl extends BaseService implements UserService {

  private final UserMapper userMapper;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  UserServiceImpl(
      UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userMapper = userMapper;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UUID createUser(CreateUserDTO dto) {
    User entity = userMapper.toEntity(dto);
    entity.setPassword(passwordEncoder.encode(entity.getPassword()));
    entity.setIsDeleted(false);
    return userRepository.save(entity).getId();
  }

  @Override
  public ViewUserDTO viewUser(UUID id) {
    Optional<User> result = userRepository.findById(id);
    if (result.isEmpty()) {
      throw new RecordNotFoundError(Map.of("id", id));
    }
    return userMapper.toDTO(result.get());
  }

  @Override
  public UUID updateUser(UUID id, UpdateUserDTO dto) {
    Optional<User> result = userRepository.findById(id);
    if (result.isEmpty()) {
      throw new RecordNotFoundError(Map.of("id", id));
    }

    User entity = result.get();
    Boolean[] matched = checkRecordMatched(entity, dto.id(), dto.version());
    if (!matched[0] || !matched[1]) {
      RecordNotMatchedError error = new RecordNotMatchedError(matched, "id", "version");
      throw error;
    }

    entity.setName(dto.name());
    return userRepository.save(entity).getId();
  }

  @Override
  public void deleteUser(UUID id, DeleteUserDTO dto) {
    Optional<User> result = userRepository.findById(id);
    if (result.isEmpty()) {
      throw new RecordNotFoundError(Map.of("id", id));
    }

    User entity = result.get();
    Boolean[] matched = checkRecordMatched(entity, dto.id(), dto.version());
    if (!matched[0] || !matched[1]) {
      throw new RecordNotMatchedError(matched, "id", "version");
    }

    entity.setIsDeleted(true);
    userRepository.save(entity);
  }
}
