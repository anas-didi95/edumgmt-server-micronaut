/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance.dto;

import io.micronaut.serde.annotation.Serdeable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Serdeable
public record ViewAttendanceDTO(
    UUID id,
    LocalDate date,
    String remark,
    Boolean isDeleted,
    Integer version,
    String createdBy,
    Instant createdDate,
    String updatedBy,
    Instant updatedDate)
    implements IAttendanceDTO {}
