/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance.dto;

import io.micronaut.serde.annotation.Serdeable;
import java.util.List;
import java.util.UUID;

@Serdeable
public record SearchAttendanceStudentDTO(
    List<ResultDTO> resultList,
    Integer page,
    Integer totalPages,
    Integer recordsPerPage,
    Long totalRecords)
    implements IAttendanceDTO {

  @Serdeable
  public static record ResultDTO(UUID studentId) {}
}
