/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student.mapper;

import com.anasdidi.edumgmt.student.dto.CreateStudentDTO;
import com.anasdidi.edumgmt.student.dto.SearchStudentDTO;
import com.anasdidi.edumgmt.student.dto.ViewStudentDTO;
import com.anasdidi.edumgmt.student.entity.Student;
import io.micronaut.context.annotation.Mapper;
import jakarta.inject.Singleton;

@Singleton
public interface StudentMapper {

  @Mapper
  Student toEntity(CreateStudentDTO dto);

  @Mapper
  ViewStudentDTO toDTO(Student entity);

  @Mapper
  SearchStudentDTO.ResultDTO toResultDTO(Student entity);
}
