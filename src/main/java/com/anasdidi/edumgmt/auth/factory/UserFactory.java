/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.factory;

import com.anasdidi.edumgmt.auth.entity.User;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.beans.BeanWrapper;
import io.micronaut.data.event.EntityEventContext;
import io.micronaut.data.event.listeners.PrePersistEventListener;
import jakarta.inject.Singleton;

@Factory
class UserFactory {

  @Singleton
  PrePersistEventListener<User> prePresistUser() {
    return new PrePersistEventListener<User>() {

      @Override
      public boolean prePersist(@NonNull User entity) {
        throw new UnsupportedOperationException("Unimplemented method 'prePersist'");
      }

      @Override
      public boolean prePersist(@NonNull EntityEventContext<User> context) {
        User entity = context.getEntity();
        BeanWrapper<User> bean = BeanWrapper.getWrapper(entity);

        context.setProperty(
            bean.getIntrospection().getRequiredProperty("name", String.class),
            entity.getName().toUpperCase());
        return true;
      }
    };
  }
}
