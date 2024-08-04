/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/attendance")
class AttendanceController implements IAttendanceController {

  @Override
  @Get
  public HttpResponse<Void> helloWorld() {
    return HttpResponse.ok();
  }
}
