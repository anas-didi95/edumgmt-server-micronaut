/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt;

import com.anasdidi.edumgmt.student.entity.Student;
import io.micronaut.context.MessageSource;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.i18n.ResourceBundleMessageSource;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.beans.BeanWrapper;
import io.micronaut.data.event.EntityEventContext;
import io.micronaut.data.event.listeners.PrePersistEventListener;
import jakarta.inject.Singleton;

@Factory
public class EdumgmtFactory {

  @Singleton
  public MessageSource messageSource() {
    return new ResourceBundleMessageSource("i18n.messages");
  }

  @Singleton
  PrePersistEventListener<Student> beforeStudentPersist() {
    String userId = "SYSTEM";
    return new PrePersistEventListener<Student>() {

      @Override
      public boolean prePersist(@NonNull Student entity) {
        throw new UnsupportedOperationException("Unimplemented method 'prePersist'");
      }

      @Override
      public boolean prePersist(@NonNull EntityEventContext<Student> context) {
        Student entity = context.getEntity();
        BeanWrapper<Student> bean = BeanWrapper.getWrapper(entity);

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
}
