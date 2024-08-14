/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.entity;

import com.anasdidi.edumgmt.auth.converter.RolesAttributeConverter;
import com.anasdidi.edumgmt.common.entity.BaseEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Set;

@Serdeable
@Entity
@Table(name = "T_USER")
public class User extends BaseEntity {

  @Column(name = "USER_ID")
  private String userId;

  @Column(name = "PWD")
  private String password;

  @Column(name = "NM")
  private String name;

  @Column(name = "ROLES")
  @Convert(converter = RolesAttributeConverter.class)
  private Set<String> roles;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<String> getRoles() {
    return roles;
  }

  public void setRoles(Set<String> roles) {
    this.roles = roles;
  }
}
