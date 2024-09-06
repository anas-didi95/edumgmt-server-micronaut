/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance.dto;

import com.anasdidi.edumgmt.common.dto.PaginationDTO;
import io.micronaut.serde.annotation.Serdeable;
import java.util.List;
import java.util.UUID;

@Serdeable
public record SearchAttendanceStudentDTO(List<ResultDTO> resultList, PaginationDTO pagination)
    implements IAttendanceDTO {

  @Serdeable
  public static record ResultDTO(UUID id, UUID studentId) {}
}
