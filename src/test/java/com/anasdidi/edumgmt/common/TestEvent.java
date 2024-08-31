/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.common;

import com.anasdidi.edumgmt.auth.entity.User;
import com.anasdidi.edumgmt.auth.repository.UserRepository;
import com.anasdidi.edumgmt.common.factory.CommonProps;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

@Singleton
@Requires(env = "test")
class TestEvent {

  private static final Logger logger = LoggerFactory.getLogger(TestEvent.class);
  private final UserRepository userRepository;
  private final CommonProps commonProps;
  private final PasswordEncoder passwordEncoder;

  TestEvent(
      UserRepository userRepository, CommonProps commonProps, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.commonProps = commonProps;
    this.passwordEncoder = passwordEncoder;
  }

  @EventListener
  @Transactional
  void onStartupEvent(StartupEvent event) {
    String password = passwordEncoder.encode(commonProps.getTestUser().password());

    Optional<User> result =
        userRepository.findByUserIdAndIsDeleted(commonProps.getTestUser().username(), false);
    if (result.isPresent()) {
      User entity = result.get();
      entity.setPassword(password);
      userRepository.save(entity);
      logger.info("[onStartupEvent] TestUser updated: {}", commonProps.getSuperAdmin().password());
    } else {
      User entity = new User();
      entity.setUserId(commonProps.getTestUser().username());
      entity.setPassword(password);
      entity.setName("test user");
      entity.setRoles(commonProps.getTestUser().roles());
      entity.setIsDeleted(false);
      userRepository.save(entity);
      logger.info("[onStartupEvent] TestUser created: {}", commonProps.getSuperAdmin().password());
    }
  }
}
