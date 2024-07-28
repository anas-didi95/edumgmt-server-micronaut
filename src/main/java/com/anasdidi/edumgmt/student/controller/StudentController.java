/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student.controller;

import com.anasdidi.edumgmt.student.dto.CreateStudentDTO;
import com.anasdidi.edumgmt.student.dto.ViewStudentDTO;
import com.anasdidi.edumgmt.student.service.StudentService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/student")
class StudentController implements IStudentController {

  private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
  private final StudentService studentService;

  @Inject
  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @Post
  HttpResponse<ViewStudentDTO> createStudent(@Body CreateStudentDTO reqBody) {
    logger.debug("[createStudent] reqBody={}", reqBody);
    UUID id = studentService.createStudent(reqBody);
    logger.debug("[createStudent] id={}", id);
    ViewStudentDTO resBody = studentService.viewStudent(id);
    logger.debug("[createStudent] resBody={}", resBody);
    return HttpResponse.created(resBody);
  }
}
