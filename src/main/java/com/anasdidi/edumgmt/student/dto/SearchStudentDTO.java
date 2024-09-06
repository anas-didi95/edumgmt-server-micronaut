/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student.dto;

import com.anasdidi.edumgmt.common.dto.PaginationDTO;
import io.micronaut.serde.annotation.Serdeable;
import java.util.List;
import java.util.UUID;

@Serdeable
public record SearchStudentDTO(List<ResultDTO> resultList, PaginationDTO pagination)
    implements IStudentDTO {

  @Serdeable
  public static record ResultDTO(UUID id, String idNo, String name) {}
}
