/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance.controller;

import com.anasdidi.edumgmt.attendance.dto.CreateAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.CreateAttendanceStudentDTO;
import com.anasdidi.edumgmt.attendance.dto.SearchAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.SearchAttendanceStudentDTO;
import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceStudentDTO;
import com.anasdidi.edumgmt.common.aspect.TraceLog;
import io.micronaut.http.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;

@Tag(name = "attendance")
@TraceLog
public interface AttendanceController {

  @Operation(summary = "Add new attendance record")
  HttpResponse<ViewAttendanceDTO> createAttendance(CreateAttendanceDTO reqBody);

  @Operation(summary = "Add new attendance student record")
  HttpResponse<ViewAttendanceStudentDTO> createAttendanceStudent(
      UUID attendanceId, CreateAttendanceStudentDTO reqBody);

  @Operation(summary = "Search attendance record")
  HttpResponse<SearchAttendanceDTO> searchAttendance(Integer page, Integer size);

  @Operation(summary = "Search attendance student record")
  HttpResponse<SearchAttendanceStudentDTO> searchAttendanceStudent(
      UUID attendanceId, Integer page, Integer size);
}
