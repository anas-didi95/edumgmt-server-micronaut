/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student.dto;

import io.micronaut.serde.annotation.Serdeable;
import java.util.List;
import java.util.UUID;

@Serdeable
public record SearchStudentDTO(
    List<ResultDTO> resultList,
    Integer page,
    Integer totalPages,
    Integer recordsPerPage,
    Long totalRecords)
    implements IStudentDTO {

  @Serdeable
  public static record ResultDTO(UUID id, String idNo, String name) {}
}
