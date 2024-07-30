/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student.dto;

import io.micronaut.serde.annotation.Serdeable;
import java.util.UUID;

@Serdeable
public record ViewStudentDTO(UUID id, String name, Boolean isDeleted, Integer version)
    implements IStudentDTO {}
