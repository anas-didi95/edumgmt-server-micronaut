/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.dto;

import com.anasdidi.edumgmt.common.dto.PaginationDTO;
import io.micronaut.serde.annotation.Serdeable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Serdeable
public record SearchUserDTO(List<ResultDTO> resultList, PaginationDTO pagination)
    implements IAuthDTO {

  @Serdeable
  public static record ResultDTO(UUID id, String userId, String name, Set<String> roles) {}
}
