/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student.service;

import com.anasdidi.edumgmt.common.aspect.TraceLog;
import com.anasdidi.edumgmt.student.dto.CreateStudentDTO;
import com.anasdidi.edumgmt.student.dto.DeleteStudentDTO;
import com.anasdidi.edumgmt.student.dto.SearchStudentDTO;
import com.anasdidi.edumgmt.student.dto.UpdateStudentDTO;
import com.anasdidi.edumgmt.student.dto.ViewStudentDTO;
import io.micronaut.data.model.Pageable;
import java.util.UUID;

@TraceLog
public interface StudentService {

  UUID createStudent(CreateStudentDTO dto);

  ViewStudentDTO viewStudent(UUID id);

  UUID updateStudent(UUID id, UpdateStudentDTO dto);

  void deleteStudent(UUID id, DeleteStudentDTO dto);

  SearchStudentDTO searchStudent(String idNo, String name, Pageable pageable);
}
