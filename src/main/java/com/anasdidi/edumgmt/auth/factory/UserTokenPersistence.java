/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.factory;

import com.anasdidi.edumgmt.auth.entity.User;
import com.anasdidi.edumgmt.auth.entity.UserToken;
import com.anasdidi.edumgmt.auth.repository.UserRepository;
import com.anasdidi.edumgmt.auth.repository.UserTokenRepository;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthenticationFailureReason;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent;
import io.micronaut.security.token.refresh.RefreshTokenPersistence;
import jakarta.inject.Singleton;
import java.util.Optional;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Singleton
class UserTokenPersistence implements RefreshTokenPersistence {

  private static final Logger logger = LoggerFactory.getLogger(UserTokenPersistence.class);
  private final UserTokenRepository userTokenRepository;
  private final UserRepository userRepository;

  UserTokenPersistence(UserTokenRepository userTokenRepository, UserRepository userRepository) {
    this.userTokenRepository = userTokenRepository;
    this.userRepository = userRepository;
  }

  @Override
  public void persistToken(RefreshTokenGeneratedEvent event) {
    if (event != null
        && event.getRefreshToken() != null
        && event.getAuthentication() != null
        && event.getAuthentication().getName() != null) {
      UserToken entity = new UserToken();
      entity.setUserId(event.getAuthentication().getName());
      entity.setToken(event.getRefreshToken());
      userTokenRepository.save(entity);
      logger.trace("[persistToken] entity.token={}", entity.getToken());
    } else {
      logger.trace("[persistToken] event={}", event != null);
    }
  }

  @Override
  public Publisher<Authentication> getAuthentication(String refreshToken) {
    return Flux.create(
        emitter -> {
          logger.trace("[getAuthentication] refreshToken={}", refreshToken);
          Optional<UserToken> result = userTokenRepository.findByToken(refreshToken);
          if (result.isPresent()) {
            UserToken entity = result.get();
            if (entity.getIsDeleted()) {
              emitter.error(
                  AuthenticationResponse.exception(AuthenticationFailureReason.ACCOUNT_EXPIRED));
            } else {
              Optional<User> result2 =
                  userRepository.findByUserIdAndIsDeleted(entity.getUserId(), false);
              result2.ifPresentOrElse(
                  user -> {
                    emitter.next(Authentication.build(user.getUserId(), user.getRoles()));
                    emitter.complete();
                  },
                  () ->
                      emitter.error(
                          AuthenticationResponse.exception(
                              AuthenticationFailureReason.USER_NOT_FOUND)));
            }
          } else {
            emitter.error(
                AuthenticationResponse.exception(
                    AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH));
          }
        },
        FluxSink.OverflowStrategy.ERROR);
  }
}
