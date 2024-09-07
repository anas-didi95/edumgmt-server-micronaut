/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student.factory;

import com.anasdidi.edumgmt.student.entity.Student;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.beans.BeanWrapper;
import io.micronaut.data.event.EntityEventContext;
import io.micronaut.data.event.listeners.PrePersistEventListener;
import io.micronaut.data.event.listeners.PreUpdateEventListener;
import jakarta.inject.Singleton;

@Factory
class StudentFactory {

  @Singleton
  PrePersistEventListener<Student> prePresistStudent() {
    return new PrePersistEventListener<Student>() {

      @Override
      public boolean prePersist(@NonNull Student entity) {
        throw new UnsupportedOperationException("Unimplemented method 'prePersist'");
      }

      @Override
      public boolean prePersist(@NonNull EntityEventContext<Student> context) {
        return handlePreSaveStudent(context);
      }
    };
  }

  @Singleton
  PreUpdateEventListener<Student> preUpdateStudent() {
    return new PreUpdateEventListener<Student>() {

      @Override
      public boolean preUpdate(@NonNull Student entity) {
        throw new UnsupportedOperationException("Unimplemented method 'preUpdate'");
      }

      @Override
      public boolean preUpdate(@NonNull EntityEventContext<Student> context) {
        return handlePreSaveStudent(context);
      }
    };
  }

  private boolean handlePreSaveStudent(@NonNull EntityEventContext<Student> context) {
    Student entity = context.getEntity();
    BeanWrapper<Student> bean = BeanWrapper.getWrapper(entity);

    context.setProperty(
        bean.getIntrospection().getRequiredProperty("name", String.class),
        entity.getName().toUpperCase());
    return true;
  }
}
