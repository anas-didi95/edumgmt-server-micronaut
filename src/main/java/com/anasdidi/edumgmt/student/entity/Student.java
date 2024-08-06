/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student.entity;

import com.anasdidi.edumgmt.common.entity.BaseEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Serdeable
@Entity
@Table(name = "T_STUDENT")
public class Student extends BaseEntity {

  @Column(name = "IC_NO")
  private String icNo;

  @Column(name = "NM")
  private String name;

  public String getIcNo() {
    return icNo;
  }

  public void setIcNo(String icNo) {
    this.icNo = icNo;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
