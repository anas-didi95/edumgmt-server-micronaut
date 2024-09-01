/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance.controller;

import com.anasdidi.edumgmt.attendance.dto.CreateAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.CreateAttendanceStudentDTO;
import com.anasdidi.edumgmt.attendance.dto.SearchAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.SearchAttendanceStudentDTO;
import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceStudentDTO;
import com.anasdidi.edumgmt.attendance.service.AttendanceService;
import com.anasdidi.edumgmt.common.util.CommonConstants;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import jakarta.inject.Inject;
import java.util.UUID;

@Controller("/attendance")
class AttendanceControllerImpl implements AttendanceController {

  private final AttendanceService attendanceService;

  @Inject
  AttendanceControllerImpl(AttendanceService attendanceService) {
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

  @Override
  @Get
  public HttpResponse<SearchAttendanceDTO> searchAttendance(
      @QueryValue(defaultValue = CommonConstants.SEARCH_DEFAULT_PAGE) Integer page,
      @QueryValue(defaultValue = CommonConstants.SEARCH_DEFAULT_SIZE) Integer size) {
    SearchAttendanceDTO resBody = attendanceService.searchAttendance(Pageable.from(page, size));
    return HttpResponse.ok(resBody);
  }

  @Override
  @Get("/{attendanceId}")
  public HttpResponse<SearchAttendanceStudentDTO> searchAttendanceStudent(
      @PathVariable UUID attendanceId,
      @QueryValue(defaultValue = CommonConstants.SEARCH_DEFAULT_PAGE) Integer page,
      @QueryValue(defaultValue = CommonConstants.SEARCH_DEFAULT_SIZE) Integer size) {
    SearchAttendanceStudentDTO resBody =
        attendanceService.searchAttendanceStudent(attendanceId, Pageable.from(page, size));
    return HttpResponse.ok(resBody);
  }
}
