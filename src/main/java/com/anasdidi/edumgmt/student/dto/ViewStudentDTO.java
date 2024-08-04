/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student.dto;

import io.micronaut.serde.annotation.Serdeable;
import java.time.Instant;
import java.util.UUID;

@Serdeable
public record ViewStudentDTO(
    UUID id,
    String icNo,
    String name,
    Boolean isDeleted,
    Integer version,
    String createdBy,
    Instant createdDate,
    String updatedBy,
    Instant updatedDate)
    implements IStudentDTO {}
