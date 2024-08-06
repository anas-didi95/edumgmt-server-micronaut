/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance.entity;

import com.anasdidi.edumgmt.common.entity.BaseEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Serdeable
@Entity
@Table(name = "T_ATTENDANCE")
public class Attendance extends BaseEntity {

  @Column(name = "DT")
  private LocalDate date;

  @Column(name = "REMARK")
  private String remark;

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
