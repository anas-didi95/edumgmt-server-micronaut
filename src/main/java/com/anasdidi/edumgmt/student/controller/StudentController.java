/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student.controller;

import com.anasdidi.edumgmt.common.aspect.TraceLog;
import com.anasdidi.edumgmt.student.dto.CreateStudentDTO;
import com.anasdidi.edumgmt.student.dto.DeleteStudentDTO;
import com.anasdidi.edumgmt.student.dto.UpdateStudentDTO;
import com.anasdidi.edumgmt.student.dto.ViewStudentDTO;
import io.micronaut.http.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;

@Tag(name = "student")
@TraceLog
public interface StudentController {

  @Operation(summary = "Add new student record")
  HttpResponse<ViewStudentDTO> createStudent(CreateStudentDTO reqBody);

  @Operation(summary = "Get current student record")
  HttpResponse<ViewStudentDTO> viewStudent(UUID id);

  @Operation(summary = "Update current student record")
  HttpResponse<ViewStudentDTO> updateStudent(UUID id, UpdateStudentDTO reqBody);

  @Operation(summary = "Delete current student record")
  HttpResponse<Void> deleteStudent(UUID id, DeleteStudentDTO reqBody);
}
