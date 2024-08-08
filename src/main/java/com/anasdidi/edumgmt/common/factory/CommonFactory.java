/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.common.factory;

import com.anasdidi.edumgmt.common.entity.BaseEntity;
import io.micronaut.context.MessageSource;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.i18n.ResourceBundleMessageSource;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.beans.BeanWrapper;
import io.micronaut.data.event.EntityEventContext;
import io.micronaut.data.event.listeners.PrePersistEventListener;
import io.micronaut.security.authentication.AuthenticationFailureReason;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.provider.HttpRequestAuthenticationProvider;
import jakarta.inject.Singleton;

@Factory
class CommonFactory {

  @Singleton
  MessageSource messageSource() {
    return new ResourceBundleMessageSource("i18n.messages");
  }

  @Singleton
  PrePersistEventListener<BaseEntity> prePresistEntity() {
    String userId = "SYSTEM";
    return new PrePersistEventListener<BaseEntity>() {

      @Override
      public boolean prePersist(@NonNull BaseEntity entity) {
        throw new UnsupportedOperationException("Unimplemented method 'prePersist'");
      }

      @Override
      public boolean prePersist(@NonNull EntityEventContext<BaseEntity> context) {
        BaseEntity entity = context.getEntity();
        BeanWrapper<BaseEntity> bean = BeanWrapper.getWrapper(entity);

        if (entity.getCreatedBy() == null) {
          context.setProperty(
              bean.getIntrospection().getRequiredProperty("createdBy", String.class), userId);
        }
        context.setProperty(
            bean.getIntrospection().getRequiredProperty("updatedBy", String.class), userId);

        return true;
      }
    };
  }

  @Singleton
  HttpRequestAuthenticationProvider<?> authenticationProvider() {
    return (requestContext, authRequest) -> {
      return authRequest.getIdentity().equals("sherlock")
              && authRequest.getSecret().equals("password")
          ? AuthenticationResponse.success(authRequest.getIdentity())
          : AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH);
    };
  }
}
