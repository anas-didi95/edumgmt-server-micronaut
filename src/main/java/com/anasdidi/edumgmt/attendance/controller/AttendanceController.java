/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance.controller;

import com.anasdidi.edumgmt.attendance.dto.CreateAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.CreateAttendanceStudentDTO;
import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceStudentDTO;
import io.micronaut.http.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;

@Tag(name = "attendance")
public interface AttendanceController {

  @Operation(summary = "Add new attendance record")
  HttpResponse<ViewAttendanceDTO> createAttendance(CreateAttendanceDTO reqBody);

  @Operation(summary = "Add new attendance student record")
  HttpResponse<ViewAttendanceStudentDTO> createAttendanceStudent(
      UUID attendanceId, CreateAttendanceStudentDTO reqBody);
}
