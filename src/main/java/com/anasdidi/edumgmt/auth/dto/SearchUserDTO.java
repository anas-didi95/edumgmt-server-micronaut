/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.dto;

import io.micronaut.serde.annotation.Serdeable;
import java.util.List;

@Serdeable
public record SearchUserDTO(
    List<ResultDTO> resultList,
    Integer page,
    Integer totalPages,
    Integer recordsPerPage,
    Long totalRecords)
    implements IAuthDTO {

  @Serdeable
  public static record ResultDTO() {}
}
