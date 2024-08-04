/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance.controller;

import io.micronaut.http.HttpResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "attendance")
interface IAttendanceController {

  HttpResponse<Void> helloWorld();
}
