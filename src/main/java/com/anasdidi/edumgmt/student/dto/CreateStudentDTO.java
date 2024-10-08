/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record CreateStudentDTO(String idNo, String name) implements IStudentDTO {}
