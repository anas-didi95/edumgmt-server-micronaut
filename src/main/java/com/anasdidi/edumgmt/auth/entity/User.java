/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.entity;

import com.anasdidi.edumgmt.common.entity.BaseEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

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
}
