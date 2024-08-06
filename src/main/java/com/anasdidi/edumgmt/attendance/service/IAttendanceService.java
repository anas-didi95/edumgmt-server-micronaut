/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance.service;

import com.anasdidi.edumgmt.attendance.dto.CreateAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.CreateAttendanceStudentDTO;
import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceDTO;
import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceStudentDTO;
import java.util.UUID;

public interface IAttendanceService {

  UUID createAttendance(CreateAttendanceDTO dto);

  ViewAttendanceDTO viewAttendance(UUID id);

  UUID createAttendanceStudent(UUID attendanceId, CreateAttendanceStudentDTO dto);

  ViewAttendanceStudentDTO viewAttendanceStudent(UUID attendanceStudentId);
}
