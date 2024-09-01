/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.service;

import com.anasdidi.edumgmt.auth.dto.SignOutDTO;
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
  public SignOutDTO signOut(Principal principal) {
    Number count = userTokenRepository.updateByUserId(principal.getName(), true);
    return new SignOutDTO(count.intValue());
  }
}
