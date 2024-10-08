/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.service;

import com.anasdidi.edumgmt.auth.repository.UserTokenRepository;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import java.security.Principal;

@Singleton
@Transactional
class AuthServiceImpl implements AuthService {

  private final UserTokenRepository userTokenRepository;

  AuthServiceImpl(UserTokenRepository userTokenRepository) {
    this.userTokenRepository = userTokenRepository;
  }

  @Override
  public Number logout(Principal principal) {
    return userTokenRepository.updateByUserIdAndIsDeleted(principal.getName(), false, true);
  }
}
