/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.module.student;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

@Controller("/student")
class StudentController {

  private final StudentService<Student, Student> addStudent;

  @Inject
  StudentController(StudentService<Student, Student> addStudent) {
    this.addStudent = addStudent;
  }

  @Post(uris = {"", "/"})
  HttpResponse<Student> addStudent(@Body Student requestBody) {
    return HttpResponse.created(addStudent.process(requestBody));
  }
}
