/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.dto;

import io.micronaut.serde.annotation.Serdeable;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Serdeable
public record ViewUserDTO(
    UUID id,
    String userId,
    String name,
    Set<String> roles,
    Boolean isDeleted,
    Integer version,
    String createdBy,
    Instant createdDate,
    String updatedBy,
    Instant updatedDate)
    implements IAuthDTO {}
