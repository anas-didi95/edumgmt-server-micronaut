/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student.service;

import com.anasdidi.edumgmt.student.dto.CreateStudentDTO;
import com.anasdidi.edumgmt.student.dto.DeleteStudentDTO;
import com.anasdidi.edumgmt.student.dto.UpdateStudentDTO;
import com.anasdidi.edumgmt.student.dto.ViewStudentDTO;
import java.util.UUID;

public interface StudentService {

  UUID createStudent(CreateStudentDTO dto);

  ViewStudentDTO viewStudent(UUID id);

  UUID updateStudent(UUID id, UpdateStudentDTO dto);

  void deleteStudent(UUID id, DeleteStudentDTO dto);
}
