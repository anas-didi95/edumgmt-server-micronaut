/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance.entity;

import com.anasdidi.edumgmt.common.entity.BaseEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;

@Serdeable
@Entity
@Table(name = "T_ATTENDANCE_STUDENT")
public class AttendanceStudent extends BaseEntity {

  @Column(name = "ATTENDANCE_ID")
  private UUID attendanceId;

  @Column(name = "STUDENT_ID")
  private UUID studentId;

  public UUID getAttendanceId() {
    return attendanceId;
  }

  public void setAttendanceId(UUID attendanceId) {
    this.attendanceId = attendanceId;
  }

  public UUID getStudentId() {
    return studentId;
  }

  public void setStudentId(UUID studentId) {
    this.studentId = studentId;
  }
}
