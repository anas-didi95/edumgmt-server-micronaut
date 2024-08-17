/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.factory;

import com.anasdidi.edumgmt.auth.entity.User;
import com.anasdidi.edumgmt.auth.entity.UserToken;
import com.anasdidi.edumgmt.auth.repository.UserRepository;
import com.anasdidi.edumgmt.auth.repository.UserTokenRepository;
import io.micronaut.context.annotation.Factory;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthenticationFailureReason;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent;
import io.micronaut.security.token.refresh.RefreshTokenPersistence;
import jakarta.inject.Singleton;
import java.util.Optional;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Factory
class UserTokenFactory {

  private final UserTokenRepository userTokenRepository;
  private final UserRepository userRepository;

  UserTokenFactory(UserTokenRepository userTokenRepository, UserRepository userRepository) {
    this.userTokenRepository = userTokenRepository;
    this.userRepository = userRepository;
  }

  @Singleton
  RefreshTokenPersistence userTokenPersistence() {
    return new RefreshTokenPersistence() {

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
        }
      }

      @Override
      public Publisher<Authentication> getAuthentication(String refreshToken) {
        return Flux.create(
            emitter -> {
              Optional<UserToken> result = userTokenRepository.findByToken(refreshToken);
              if (result.isPresent()) {
                UserToken entity = result.get();
                if (entity.getIsDeleted()) {
                  emitter.error(
                      AuthenticationResponse.exception(
                          AuthenticationFailureReason.ACCOUNT_EXPIRED));
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
    };
  }
}
