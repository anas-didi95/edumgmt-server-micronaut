/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance.service;

import com.anasdidi.edumgmt.attendance.dto.CreateAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceDTO;
import com.anasdidi.edumgmt.attendance.entity.Attendance;
import com.anasdidi.edumgmt.attendance.mapper.AttendanceMapper;
import com.anasdidi.edumgmt.attendance.repository.AttendanceRepository;
import com.anasdidi.edumgmt.exception.error.RecordNotFoundError;
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
class AttendanceService implements IAttendanceService {

  private static final Logger logger = LoggerFactory.getLogger(AttendanceService.class);
  private final AttendanceRepository attendanceRepository;
  private final AttendanceMapper attendanceMapper;

  @Inject
  AttendanceService(AttendanceRepository attendanceRepository, AttendanceMapper attendanceMapper) {
    this.attendanceRepository = attendanceRepository;
    this.attendanceMapper = attendanceMapper;
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
}
