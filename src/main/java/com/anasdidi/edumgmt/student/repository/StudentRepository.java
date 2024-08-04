/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student.repository;

import com.anasdidi.edumgmt.student.entity.Student;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.UUID;

@Repository
public interface StudentRepository extends CrudRepository<Student, UUID> {}
