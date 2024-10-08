/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.handler;

import com.anasdidi.edumgmt.auth.entity.User;
import com.anasdidi.edumgmt.auth.entity.UserToken;
import com.anasdidi.edumgmt.auth.repository.UserRepository;
import com.anasdidi.edumgmt.auth.repository.UserTokenRepository;
import com.anasdidi.edumgmt.common.factory.CommonProps;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthenticationFailureReason;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.token.Claims;
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent;
import io.micronaut.security.token.refresh.RefreshTokenPersistence;
import jakarta.inject.Singleton;
import java.util.Map;
import java.util.Optional;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Singleton
class AuthTokenHandler implements RefreshTokenPersistence {

  private static final Logger logger = LoggerFactory.getLogger(AuthTokenHandler.class);
  private final UserTokenRepository userTokenRepository;
  private final UserRepository userRepository;
  private final CommonProps commonProps;

  AuthTokenHandler(
      UserTokenRepository userTokenRepository,
      UserRepository userRepository,
      CommonProps commonProps) {
    this.userTokenRepository = userTokenRepository;
    this.userRepository = userRepository;
    this.commonProps = commonProps;
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
      entity.setIsDeleted(false);
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
                  AuthenticationResponse.exception(AuthenticationFailureReason.ACCOUNT_EXPIRED));
            } else {
              Optional<User> result2 =
                  userRepository.findByUserIdAndIsDeleted(entity.getUserId(), false);
              result2.ifPresentOrElse(
                  user -> {
                    Map<String, Object> attributeMap =
                        Map.of(Claims.ISSUER, commonProps.getJwt().issuer());
                    emitter.next(
                        Authentication.build(user.getUserId(), user.getRoles(), attributeMap));

                    logger.debug("[refreshToken] userId={}", user.getUserId());

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
