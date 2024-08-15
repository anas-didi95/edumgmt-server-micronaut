/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.common.event;

import com.anasdidi.edumgmt.auth.entity.User;
import com.anasdidi.edumgmt.auth.repository.UserRepository;
import com.anasdidi.edumgmt.common.factory.CommonProps;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

@Singleton
class CommonEvent {

  private static final String SUPERADMIN_ID = "super-admin";
  private static final Logger logger = LoggerFactory.getLogger(CommonEvent.class);
  private final UserRepository userRepository;
  private final CommonProps commonProps;
  private final PasswordEncoder passwordEncoder;

  CommonEvent(
      UserRepository userRepository, CommonProps commonProps, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.commonProps = commonProps;
    this.passwordEncoder = passwordEncoder;
  }

  @EventListener
  @Transactional
  void onStartupEvent(StartupEvent event) {
    Optional<User> result = userRepository.findByUserIdAndIsDeleted(SUPERADMIN_ID, false);
    if (result.isPresent()) {
      User entity = result.get();
      entity.setPassword(passwordEncoder.encode(commonProps.getSuperAdmin().password()));
      userRepository.save(entity);
      logger.info(
          "[onStartupEvent] SuperAdmin updated: {}", commonProps.getSuperAdmin().password());
    } else {
      User entity = new User();
      entity.setUserId(SUPERADMIN_ID);
      entity.setPassword(passwordEncoder.encode(commonProps.getSuperAdmin().password()));
      entity.setName("SuperAdmin");
      entity.setRoles(Set.of("ROLE_SUPERADMIN"));
      userRepository.save(entity);
      logger.info(
          "[onStartupEvent] SuperAdmin created: {}", commonProps.getSuperAdmin().password());
    }
  }
}
