/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance.dto;

import io.micronaut.core.convert.format.Format;
import io.micronaut.serde.annotation.Serdeable;
import java.time.LocalDate;

@Serdeable
public record CreateAttendanceDTO(@Format("yyyy-MM-dd") LocalDate date) implements IAttendanceDTO {}
