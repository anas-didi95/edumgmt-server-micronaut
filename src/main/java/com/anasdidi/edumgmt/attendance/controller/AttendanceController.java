/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance.controller;

import com.anasdidi.edumgmt.attendance.dto.CreateAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.CreateAttendanceStudentDTO;
import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceStudentDTO;
import com.anasdidi.edumgmt.attendance.service.IAttendanceService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import java.util.UUID;

@Controller("/attendance")
class AttendanceController implements IAttendanceController {

  private final IAttendanceService attendanceService;

  @Inject
  AttendanceController(IAttendanceService attendanceService) {
    this.attendanceService = attendanceService;
  }

  @Override
  @Post
  public HttpResponse<ViewAttendanceDTO> createAttendance(@Body CreateAttendanceDTO reqBody) {
    UUID id = attendanceService.createAttendance(reqBody);
    ViewAttendanceDTO resBody = attendanceService.viewAttendance(id);
    return HttpResponse.created(resBody);
  }

  @Override
  @Post("/{attendanceId}")
  public HttpResponse<ViewAttendanceStudentDTO> createAttendanceStudent(
      @PathVariable UUID attendanceId, @Body CreateAttendanceStudentDTO reqBody) {
    UUID attendanceStudentId = attendanceService.createAttendanceStudent(attendanceId, reqBody);
    ViewAttendanceStudentDTO resBody = attendanceService.viewAttendanceStudent(attendanceStudentId);
    return HttpResponse.ok(resBody);
  }
}
