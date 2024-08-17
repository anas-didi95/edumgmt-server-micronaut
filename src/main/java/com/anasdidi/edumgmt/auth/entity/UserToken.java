/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.entity;

import com.anasdidi.edumgmt.common.entity.BaseEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Serdeable
@Entity
@Table(name = "T_USER_TOKEN")
public class UserToken extends BaseEntity {

  @Column(name = "USER_ID")
  private String userId;

  @Column(name = "TOKEN")
  private String token;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
