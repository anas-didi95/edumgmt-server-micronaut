/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.service;

import com.anasdidi.edumgmt.auth.dto.LogoutUserDTO;
import com.anasdidi.edumgmt.auth.repository.UserTokenRepository;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Transactional
class AuthService implements IAuthService {

  private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
  private final UserTokenRepository userTokenRepository;

  AuthService(UserTokenRepository userTokenRepository) {
    this.userTokenRepository = userTokenRepository;
  }

  @Override
  public LogoutUserDTO logout(Principal principal) {
    Number count = userTokenRepository.updateByUserId(principal.getName(), true);
    logger.trace("[trace] principal.name={}, count={}", principal.getName(), count.intValue());
    return new LogoutUserDTO(count.intValue());
  }
}
