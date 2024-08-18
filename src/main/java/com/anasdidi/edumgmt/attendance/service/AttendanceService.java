/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance.service;

import com.anasdidi.edumgmt.attendance.dto.CreateAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.CreateAttendanceStudentDTO;
import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceStudentDTO;
import com.anasdidi.edumgmt.common.aspect.TraceLog;
import java.util.UUID;

@TraceLog
public interface AttendanceService {

  UUID createAttendance(CreateAttendanceDTO dto);

  ViewAttendanceDTO viewAttendance(UUID id);

  UUID createAttendanceStudent(UUID attendanceId, CreateAttendanceStudentDTO dto);

  ViewAttendanceStudentDTO viewAttendanceStudent(UUID attendanceStudentId);
}
