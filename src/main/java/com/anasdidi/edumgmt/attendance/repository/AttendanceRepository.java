/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance.repository;

import com.anasdidi.edumgmt.attendance.entity.Attendance;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends CrudRepository<Attendance, UUID> {}
