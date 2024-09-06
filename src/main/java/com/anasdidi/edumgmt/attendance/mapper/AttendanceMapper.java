/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance.mapper;

import com.anasdidi.edumgmt.attendance.dto.CreateAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.SearchAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.SearchAttendanceStudentDTO;
import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceDTO;
import com.anasdidi.edumgmt.attendance.entity.Attendance;
import com.anasdidi.edumgmt.attendance.entity.AttendanceStudent;
import io.micronaut.context.annotation.Mapper;
import jakarta.inject.Singleton;

@Singleton
public interface AttendanceMapper {

  @Mapper
  Attendance toEntity(CreateAttendanceDTO dto);

  @Mapper
  ViewAttendanceDTO toDTO(Attendance entity);

  @Mapper
  SearchAttendanceDTO.ResultDTO toResultDTO(Attendance entity);

  @Mapper
  SearchAttendanceStudentDTO.ResultDTO toResultDTO(AttendanceStudent entity);
}
