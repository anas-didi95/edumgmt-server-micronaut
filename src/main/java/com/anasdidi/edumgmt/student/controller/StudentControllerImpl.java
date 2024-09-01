/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student.controller;

import com.anasdidi.edumgmt.common.util.CommonConstants;
import com.anasdidi.edumgmt.student.dto.CreateStudentDTO;
import com.anasdidi.edumgmt.student.dto.DeleteStudentDTO;
import com.anasdidi.edumgmt.student.dto.SearchStudentDTO;
import com.anasdidi.edumgmt.student.dto.UpdateStudentDTO;
import com.anasdidi.edumgmt.student.dto.ViewStudentDTO;
import com.anasdidi.edumgmt.student.service.StudentService;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.UUID;

@Controller("/student")
class StudentControllerImpl implements StudentController {

  private final StudentService studentService;

  @Inject
  StudentControllerImpl(StudentService studentService) {
    this.studentService = studentService;
  }

  @Post
  public HttpResponse<ViewStudentDTO> createStudent(@Body CreateStudentDTO reqBody) {
    UUID id = studentService.createStudent(reqBody);
    ViewStudentDTO resBody = studentService.viewStudent(id);
    return HttpResponse.created(resBody);
  }

  @Get("/{id}")
  public HttpResponse<ViewStudentDTO> viewStudent(@PathVariable UUID id) {
    ViewStudentDTO resBody = studentService.viewStudent(id);
    return HttpResponse.ok(resBody);
  }

  @Put("/{id}")
  public HttpResponse<ViewStudentDTO> updateStudent(
      @PathVariable UUID id, @Body UpdateStudentDTO reqBody) {
    UUID update = studentService.updateStudent(id, reqBody);
    ViewStudentDTO resBody = studentService.viewStudent(update);
    return HttpResponse.ok(resBody);
  }

  @Delete("/{id}")
  public HttpResponse<Void> deleteStudent(@PathVariable UUID id, @Body DeleteStudentDTO reqBody) {
    studentService.deleteStudent(id, reqBody);
    return HttpResponse.noContent();
  }

  @Override
  @Get
  public HttpResponse<SearchStudentDTO> searchStudent(
      @QueryValue Optional<String> idNo,
      @QueryValue Optional<String> name,
      @QueryValue(defaultValue = CommonConstants.SEARCH_DEFAULT_PAGE) Integer page,
      @QueryValue(defaultValue = CommonConstants.SEARCH_DEFAULT_SIZE) Integer size) {
    SearchStudentDTO resBody =
        studentService.searchStudent(
            idNo.orElse(""),
            name.orElse(""),
            Pageable.from(page, size, CommonConstants.SEARCH_DEFAULT_SORT));
    return HttpResponse.ok(resBody);
  }
}
