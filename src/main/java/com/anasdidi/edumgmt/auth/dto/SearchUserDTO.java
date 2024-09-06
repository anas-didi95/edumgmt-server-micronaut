/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.dto;

import io.micronaut.serde.annotation.Serdeable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Serdeable
public record SearchUserDTO(
    List<ResultDTO> resultList,
    Integer page,
    Integer totalPages,
    Integer recordsPerPage,
    Long totalRecords)
    implements IAuthDTO {

  @Serdeable
  public static record ResultDTO(UUID id, String userId, String name, Set<String> roles) {}
}
