/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student.controller;

import com.anasdidi.edumgmt.student.dto.CreateStudentDTO;
import com.anasdidi.edumgmt.student.dto.DeleteStudentDTO;
import com.anasdidi.edumgmt.student.dto.UpdateStudentDTO;
import com.anasdidi.edumgmt.student.dto.ViewStudentDTO;
import com.anasdidi.edumgmt.student.service.IStudentService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import jakarta.inject.Inject;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/student")
class StudentController implements IStudentController {

  private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
  private final IStudentService studentService;

  @Inject
  StudentController(IStudentService studentService) {
    this.studentService = studentService;
  }

  @Post
  public HttpResponse<ViewStudentDTO> createStudent(@Body CreateStudentDTO reqBody) {
    logger.trace("[createStudent] reqBody={}", reqBody);

    UUID id = studentService.createStudent(reqBody);
    logger.trace("[createStudent] id={}", id);

    ViewStudentDTO resBody = studentService.viewStudent(id);
    logger.trace("[createStudent] resBody={}", resBody);

    return HttpResponse.created(resBody);
  }

  @Get("/{id}")
  public HttpResponse<ViewStudentDTO> viewStudent(@PathVariable UUID id) {
    logger.trace("[viewStudent] id={}", id);

    ViewStudentDTO resBody = studentService.viewStudent(id);
    logger.trace("[viewStudent] resBody={}", resBody);

    return HttpResponse.ok(resBody);
  }

  @Put("/{id}")
  public HttpResponse<ViewStudentDTO> updateStudent(
      @PathVariable UUID id, @Body UpdateStudentDTO reqBody) {
    logger.trace("[updateStudent] id={}, reqBody={}", id, reqBody);
    UUID update = studentService.updateStudent(id, reqBody);
    ViewStudentDTO resBody = studentService.viewStudent(update);
    return HttpResponse.ok(resBody);
  }

  @Delete("/{id}")
  public HttpResponse<Void> deleteStudent(@PathVariable UUID id, @Body DeleteStudentDTO reqBody) {
    studentService.deleteStudent(id, reqBody);
    return HttpResponse.noContent();
  }
}
