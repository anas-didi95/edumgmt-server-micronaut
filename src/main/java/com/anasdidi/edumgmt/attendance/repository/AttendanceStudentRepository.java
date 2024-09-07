/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance.repository;

import com.anasdidi.edumgmt.attendance.dto.ViewAttendanceStudentDTO;
import com.anasdidi.edumgmt.attendance.entity.AttendanceStudent;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.repository.PageableRepository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttendanceStudentRepository extends PageableRepository<AttendanceStudent, UUID> {

  @Query(
      """
      SELECT new ViewAttendanceStudentDTO(a.id, b.date, c.name)
      FROM AttendanceStudent a
      INNER JOIN Attendance b on b.id = a.attendanceId
      INNER JOIN Student c on c.id = a.studentId
      WHERE a.id = :attendanceStudentId
      """)
  Optional<ViewAttendanceStudentDTO> viewAttendanceStudent(UUID attendanceStudentId);

  @Query(
      value =
          """
          SELECT new ViewAttendanceStudentDTO(a.id, b.date, c.name)
          FROM AttendanceStudent a
          INNER JOIN Attendance b on b.id = a.attendanceId
          INNER JOIN Student c on c.id = a.studentId
          WHERE a.attendanceId = :attendanceId
          """,
      countQuery =
          """
          SELECT count(1)
          FROM AttendanceStudent a
          INNER JOIN Attendance b on b.id = a.attendanceId
          INNER JOIN Student c on c.id = a.studentId
          WHERE a.attendanceId = :attendanceId
          """)
  Page<ViewAttendanceStudentDTO> searchAttendanceStudent(UUID attendanceId, Pageable pageable);
}
