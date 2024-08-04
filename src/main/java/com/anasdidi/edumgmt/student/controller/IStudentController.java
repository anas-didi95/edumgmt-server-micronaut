/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student.controller;

import com.anasdidi.edumgmt.student.dto.CreateStudentDTO;
import com.anasdidi.edumgmt.student.dto.DeleteStudentDTO;
import com.anasdidi.edumgmt.student.dto.UpdateStudentDTO;
import com.anasdidi.edumgmt.student.dto.ViewStudentDTO;
import io.micronaut.http.HttpResponse;
import java.util.UUID;

interface IStudentController {

  HttpResponse<ViewStudentDTO> createStudent(CreateStudentDTO reqBody);

  HttpResponse<ViewStudentDTO> viewStudent(UUID id);

  HttpResponse<ViewStudentDTO> updateStudent(UUID id, UpdateStudentDTO reqBody);

  HttpResponse<Void> deleteStudent(UUID id, DeleteStudentDTO reqBody);
}
