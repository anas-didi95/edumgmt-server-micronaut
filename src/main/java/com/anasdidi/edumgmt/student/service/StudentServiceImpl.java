/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student.service;

import com.anasdidi.edumgmt.common.service.BaseService;
import com.anasdidi.edumgmt.exception.error.RecordNotFoundError;
import com.anasdidi.edumgmt.exception.error.RecordNotMatchedError;
import com.anasdidi.edumgmt.student.dto.CreateStudentDTO;
import com.anasdidi.edumgmt.student.dto.DeleteStudentDTO;
import com.anasdidi.edumgmt.student.dto.SearchStudentDTO;
import com.anasdidi.edumgmt.student.dto.UpdateStudentDTO;
import com.anasdidi.edumgmt.student.dto.ViewStudentDTO;
import com.anasdidi.edumgmt.student.entity.Student;
import com.anasdidi.edumgmt.student.mapper.StudentMapper;
import com.anasdidi.edumgmt.student.repository.StudentRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Singleton
@Transactional
class StudentServiceImpl extends BaseService implements StudentService {

  private final StudentRepository studentRepository;
  private final StudentMapper studentMapper;

  @Inject
  StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
    this.studentRepository = studentRepository;
    this.studentMapper = studentMapper;
  }

  @Override
  public UUID createStudent(CreateStudentDTO dto) {
    Student entity = studentMapper.toEntity(dto);
    entity.setIsDeleted(false);
    return studentRepository.save(entity).getId();
  }

  @Override
  public ViewStudentDTO viewStudent(UUID id) {
    Optional<Student> result = studentRepository.findById(id);
    if (result.isEmpty()) {
      throw new RecordNotFoundError(Map.of("id", id));
    }
    return studentMapper.toDTO(result.get());
  }

  @Override
  public UUID updateStudent(UUID id, UpdateStudentDTO dto) {
    Optional<Student> result = studentRepository.findById(id);
    if (result.isEmpty()) {
      throw new RecordNotFoundError(Map.of("id", id));
    }

    Student entity = result.get();
    Boolean[] matched = checkRecordMatched(entity, dto.id(), dto.version());
    if (!matched[0] || !matched[1]) {
      throw new RecordNotMatchedError(matched, "id", "version");
    }

    entity.setName(dto.name());
    entity.setIcNo(dto.icNo());
    return studentRepository.save(entity).getId();
  }

  @Override
  public void deleteStudent(UUID id, DeleteStudentDTO dto) {
    Optional<Student> result = studentRepository.findById(id);
    if (result.isEmpty()) {
      throw new RecordNotFoundError(Map.of("id", id));
    }

    Student entity = result.get();
    Boolean[] matched = checkRecordMatched(entity, dto.id(), dto.version());
    if (!matched[0] || !matched[1]) {
      throw new RecordNotMatchedError(matched, "id", "version");
    }

    entity.setIsDeleted(true);
    studentRepository.save(entity);
  }

  @Override
  public SearchStudentDTO searchStudent(Pageable pageable) {
    Page<Student> search = studentRepository.findAll(pageable);
    return new SearchStudentDTO(
        search.getContent().stream().map(studentMapper::toResultDTO).toList(),
        search.getPageNumber(),
        search.getTotalPages(),
        search.getSize(),
        search.getTotalSize());
  }
}
