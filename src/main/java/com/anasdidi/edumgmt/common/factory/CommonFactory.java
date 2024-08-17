/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.common.factory;

import com.anasdidi.edumgmt.common.entity.BaseEntity;
import com.anasdidi.edumgmt.common.util.Constant;
import io.micronaut.context.MessageSource;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.i18n.ResourceBundleMessageSource;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.beans.BeanWrapper;
import io.micronaut.data.event.EntityEventContext;
import io.micronaut.data.event.listeners.PrePersistEventListener;
import io.micronaut.data.event.listeners.PreUpdateEventListener;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Factory
class CommonFactory {

  private static final Logger logger = LoggerFactory.getLogger(CommonFactory.class);
  private final SecurityService securityService;

  CommonFactory(SecurityService securityService) {
    this.securityService = securityService;
  }

  @Singleton
  MessageSource messageSource() {
    return new ResourceBundleMessageSource("i18n.messages");
  }

  @Singleton
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Singleton
  PrePersistEventListener<BaseEntity> prePresistEntity() {
    return new PrePersistEventListener<BaseEntity>() {

      @Override
      public boolean prePersist(@NonNull BaseEntity entity) {
        throw new UnsupportedOperationException("Unimplemented method 'prePersist'");
      }

      @Override
      public boolean prePersist(@NonNull EntityEventContext<BaseEntity> context) {
        return handlePreSaveEntity(context);
      }
    };
  }

  @Singleton
  PreUpdateEventListener<BaseEntity> preUpdateEntity() {
    return new PreUpdateEventListener<BaseEntity>() {

      @Override
      public boolean preUpdate(@NonNull BaseEntity entity) {
        throw new UnsupportedOperationException("Unimplemented method 'preUpdate'");
      }

      @Override
      public boolean preUpdate(@NonNull EntityEventContext<BaseEntity> context) {
        return handlePreSaveEntity(context);
      }
    };
  }

  private boolean handlePreSaveEntity(@NonNull EntityEventContext<BaseEntity> context) {
    String userId =
        securityService
            .getAuthentication()
            .orElse(Authentication.build(Constant.SYSTEM_USER))
            .getName();
    BaseEntity entity = context.getEntity();
    BeanWrapper<BaseEntity> bean = BeanWrapper.getWrapper(entity);

    logger.trace(
        "[handlePreSaveEntity] userId={}, securityService={}",
        userId,
        securityService.isAuthenticated());
    logger.trace(entity.getCreatedBy());

    if (entity.getCreatedBy() == null) {
      context.setProperty(
          bean.getIntrospection().getRequiredProperty("createdBy", String.class), userId);
    }
    context.setProperty(
        bean.getIntrospection().getRequiredProperty("updatedBy", String.class), userId);

    return true;
  }
}
