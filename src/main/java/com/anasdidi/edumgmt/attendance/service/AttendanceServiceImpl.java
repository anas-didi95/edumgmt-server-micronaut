/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance.service;

import com.anasdidi.edumgmt.attendance.dto.CreateAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.CreateAttendanceStudentDTO;
import com.anasdidi.edumgmt.attendance.dto.SearchAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceStudentDTO;
import com.anasdidi.edumgmt.attendance.entity.Attendance;
import com.anasdidi.edumgmt.attendance.entity.AttendanceStudent;
import com.anasdidi.edumgmt.attendance.mapper.AttendanceMapper;
import com.anasdidi.edumgmt.attendance.repository.AttendanceRepository;
import com.anasdidi.edumgmt.attendance.repository.AttendanceStudentRepository;
import com.anasdidi.edumgmt.exception.error.RecordNotFoundError;
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
class AttendanceServiceImpl implements AttendanceService {

  private final AttendanceRepository attendanceRepository;
  private final AttendanceMapper attendanceMapper;
  private final StudentRepository studentRepository;
  private final AttendanceStudentRepository attendanceStudentRepository;

  @Inject
  AttendanceServiceImpl(
      AttendanceRepository attendanceRepository,
      AttendanceMapper attendanceMapper,
      StudentRepository studentRepository,
      AttendanceStudentRepository attendanceStudentRepository) {
    this.attendanceRepository = attendanceRepository;
    this.attendanceMapper = attendanceMapper;
    this.studentRepository = studentRepository;
    this.attendanceStudentRepository = attendanceStudentRepository;
  }

  @Override
  public UUID createAttendance(CreateAttendanceDTO dto) {
    Attendance entity = attendanceMapper.toEntity(dto);
    entity.setIsDeleted(false);
    return attendanceRepository.save(entity).getId();
  }

  @Override
  public ViewAttendanceDTO viewAttendance(UUID id) {
    Optional<Attendance> result = attendanceRepository.findById(id);
    if (result.isEmpty()) {
      throw new RecordNotFoundError(Map.of("id", id));
    }
    return attendanceMapper.toDTO(result.get());
  }

  @Override
  public UUID createAttendanceStudent(UUID attendanceId, CreateAttendanceStudentDTO dto) {
    if (!attendanceRepository.existsById(attendanceId)) {
      throw new RecordNotFoundError(Map.of("attendanceId", attendanceId));
    }

    if (!studentRepository.existsById(dto.studentId())) {
      throw new RecordNotFoundError(Map.of("studentId", dto.studentId()));
    }

    AttendanceStudent entity = new AttendanceStudent();
    entity.setAttendanceId(attendanceId);
    entity.setStudentId(dto.studentId());
    entity.setIsDeleted(false);
    return attendanceStudentRepository.save(entity).getId();
  }

  @Override
  public ViewAttendanceStudentDTO viewAttendanceStudent(UUID attendanceStudentId) {
    Optional<ViewAttendanceStudentDTO> result =
        attendanceStudentRepository.viewAttendanceStudent(attendanceStudentId);
    if (result.isEmpty()) {
      throw new RecordNotFoundError(Map.of("attendanceStudentId", attendanceStudentId));
    }
    return result.get();
  }

  @Override
  public SearchAttendanceDTO searchAttendance(Pageable pageable) {
    Page<Attendance> search = attendanceRepository.findAll(pageable);
    return new SearchAttendanceDTO(
        search.getContent().stream().map(attendanceMapper::toResultDTO).toList(),
        search.getPageNumber(),
        search.getTotalPages(),
        search.getSize(),
        search.getTotalSize());
  }
}
