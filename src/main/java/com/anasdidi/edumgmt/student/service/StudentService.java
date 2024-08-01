/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student.service;

import com.anasdidi.edumgmt.exception.error.RecordNotFoundError;
import com.anasdidi.edumgmt.student.dto.CreateStudentDTO;
import com.anasdidi.edumgmt.student.dto.DeleteStudentDTO;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Transactional
public class StudentService implements IStudentService {

  private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
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
      RecordNotFoundError error = new RecordNotFoundError("viewStudent", "id", id);
      logger.debug(error.logMessage, error);
      throw error;
    }
    return studentMapper.toDTO(result.get());
  }

  @Override
  public UUID updateStudent(UUID id, UpdateStudentDTO dto) {
    Optional<Student> result = studentRepository.findById(id);
    if (result.isEmpty()) {
      RecordNotFoundError error = new RecordNotFoundError("updateStudent", "id", id);
      logger.debug(error.logMessage, error);
      throw error;
    }

    Student entity = result.get();
    if (!entity.getId().equals(dto.id()) || !entity.getVersion().equals(dto.version())) {
      // TODO: Record not matched error
      return null;
    }

    entity.setName(dto.name());
    return studentRepository.save(entity).getId();
  }

  @Override
  public void deleteStudent(UUID id, DeleteStudentDTO dto) {
    Optional<Student> result = studentRepository.findById(id);
    if (result.isEmpty()) {
      RecordNotFoundError error = new RecordNotFoundError("deleteStudent", "id", id);
      logger.debug(error.logMessage, error);
      throw error;
    }

    Student entity = result.get();
    if (!entity.getId().equals(dto.id()) && !entity.getVersion().equals(dto.version())) {
      // TODO: Record not matched error
      return;
    }

    entity.setIsDeleted(true);
    studentRepository.save(entity);
  }
}
