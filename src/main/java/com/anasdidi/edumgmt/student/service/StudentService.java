/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student.service;

import com.anasdidi.edumgmt.student.dto.CreateStudentDTO;
import com.anasdidi.edumgmt.student.dto.UpdateStudentDTO;
import com.anasdidi.edumgmt.student.dto.ViewStudentDTO;
import com.anasdidi.edumgmt.student.entity.Student;
import com.anasdidi.edumgmt.student.mapper.StudentMapper;
import com.anasdidi.edumgmt.student.repository.StudentRepository;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Optional;
import java.util.UUID;

@Singleton
@Transactional
public class StudentService implements IStudentService {

  private final StudentRepository studentRepository;
  private final StudentMapper studentMapper;

  @Inject
  public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
    this.studentRepository = studentRepository;
    this.studentMapper = studentMapper;
  }

  @Override
  public UUID createStudent(CreateStudentDTO dto) {
    Student entity = studentMapper.toEntity(dto);
    entity.setName(entity.getName().toUpperCase());
    return studentRepository.save(entity).getId();
  }

  @Override
  public ViewStudentDTO viewStudent(UUID id) {
    Optional<Student> result = studentRepository.findById(id);
    if (result.isEmpty()) {
      // TODO: No record found error
      return null;
    }
    return studentMapper.toDTO(result.get());
  }

  @Override
  public UUID updateStudent(UUID id, UpdateStudentDTO dto) {
    Optional<Student> result = studentRepository.findById(id);
    if (result.isEmpty()) {
      // TODO: No record found error
      return null;
    }

    Student entity = result.get();
    if (!entity.getId().equals(dto.id()) || !entity.getVersion().equals(dto.version())) {
      // TODO: Record not matched error
      return null;
    }

    entity.setName(dto.name());
    return studentRepository.save(entity).getId();
  }
}
