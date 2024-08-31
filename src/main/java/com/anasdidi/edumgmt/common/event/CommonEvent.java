/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.common.event;

import com.anasdidi.edumgmt.auth.entity.User;
import com.anasdidi.edumgmt.auth.repository.UserRepository;
import com.anasdidi.edumgmt.auth.util.UserConstants;
import com.anasdidi.edumgmt.common.factory.CommonProps;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

@Singleton
class CommonEvent {

  private static final Logger logger = LoggerFactory.getLogger(CommonEvent.class);
  private static final String FILE_EDUMGMT = "/var/tmp/edumgmt.data.txt";
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
    String rawPassword = commonProps.getSuperAdmin().password();
    String password = passwordEncoder.encode(rawPassword);

    Optional<User> result =
        userRepository.findByUserIdAndIsDeleted(UserConstants.SuperAdmin.ID, false);
    if (result.isPresent()) {
      User entity = result.get();
      entity.setPassword(password);
      userRepository.save(entity);
      logger.trace(
          "[onStartupEvent] SuperAdmin updated: {}", commonProps.getSuperAdmin().password());
    } else {
      User entity = new User();
      entity.setUserId(UserConstants.SuperAdmin.ID);
      entity.setPassword(password);
      entity.setName("SuperAdmin");
      entity.setRoles(Set.of(UserConstants.Role.SUPERADMIN));
      entity.setIsDeleted(false);
      userRepository.save(entity);
      logger.trace(
          "[onStartupEvent] SuperAdmin created: {}", commonProps.getSuperAdmin().password());
    }

    try (FileWriter fileWriter = new FileWriter(FILE_EDUMGMT);
        PrintWriter printWriter = new PrintWriter(fileWriter); ) {
      printWriter.println("super-admin=%s".formatted(rawPassword));
      printWriter.println("jwt-issuer=%s".formatted(commonProps.getJwt().issuer()));
      logger.info("[onStartupEvent] Write file {}", FILE_EDUMGMT);
    } catch (IOException e) {
      logger.error("[onStartupEvent] Fail to write to file!", e);
      throw new RuntimeException(e);
    }
  }
}
