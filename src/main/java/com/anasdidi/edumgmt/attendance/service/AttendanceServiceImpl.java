/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance.service;

import com.anasdidi.edumgmt.attendance.dto.CreateAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.CreateAttendanceStudentDTO;
import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceStudentDTO;
import com.anasdidi.edumgmt.attendance.entity.Attendance;
import com.anasdidi.edumgmt.attendance.entity.AttendanceStudent;
import com.anasdidi.edumgmt.attendance.mapper.AttendanceMapper;
import com.anasdidi.edumgmt.attendance.repository.AttendanceRepository;
import com.anasdidi.edumgmt.attendance.repository.AttendanceStudentRepository;
import com.anasdidi.edumgmt.exception.error.RecordNotFoundError;
import com.anasdidi.edumgmt.student.repository.StudentRepository;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Transactional
class AttendanceServiceImpl implements AttendanceService {

  private static final Logger logger = LoggerFactory.getLogger(AttendanceServiceImpl.class);
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
    return attendanceRepository.save(entity).getId();
  }

  @Override
  public ViewAttendanceDTO viewAttendance(UUID id) {
    Optional<Attendance> result = attendanceRepository.findById(id);
    if (result.isEmpty()) {
      RecordNotFoundError error = new RecordNotFoundError("viewAttendance", Map.of("id", id));
      logger.debug(error.logMessage);
      throw error;
    }
    return attendanceMapper.toDTO(result.get());
  }

  @Override
  public UUID createAttendanceStudent(UUID attendanceId, CreateAttendanceStudentDTO dto) {
    if (!attendanceRepository.existsById(attendanceId)) {
      RecordNotFoundError error =
          new RecordNotFoundError("createAttendanceStudent", Map.of("attendanceId", attendanceId));
      logger.debug(error.logMessage);
      throw error;
    }

    if (!studentRepository.existsById(dto.studentId())) {
      RecordNotFoundError error =
          new RecordNotFoundError("createAttendanceStudent", Map.of("studentId", dto.studentId()));
      logger.debug(error.logMessage);
      throw error;
    }

    AttendanceStudent entity = new AttendanceStudent();
    entity.setAttendanceId(attendanceId);
    entity.setStudentId(dto.studentId());
    return attendanceStudentRepository.save(entity).getId();
  }

  @Override
  public ViewAttendanceStudentDTO viewAttendanceStudent(UUID attendanceStudentId) {
    Optional<ViewAttendanceStudentDTO> result =
        attendanceStudentRepository.viewAttendanceStudent(attendanceStudentId);
    if (result.isEmpty()) {
      RecordNotFoundError error =
          new RecordNotFoundError(
              "viewAttendanceStudent", Map.of("attendanceStudentId", attendanceStudentId));
      logger.debug(error.logMessage);
      throw error;
    }
    return result.get();
  }
}
