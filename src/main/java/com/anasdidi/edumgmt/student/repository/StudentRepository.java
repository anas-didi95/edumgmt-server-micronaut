/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student.repository;

import com.anasdidi.edumgmt.student.entity.Student;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.repository.PageableRepository;
import io.micronaut.data.repository.jpa.JpaSpecificationExecutor;
import io.micronaut.data.repository.jpa.criteria.QuerySpecification;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

@Repository
public interface StudentRepository
    extends PageableRepository<Student, UUID>, JpaSpecificationExecutor<Student> {

  default Page<Student> searchStudent(String idNo, String name, Pageable pageable) {
    QuerySpecification<Student> spec = (root, query, criteriaBuilder) -> null;
    if (StringUtils.isNotBlank(idNo)) {
      spec = spec.or(Spec.idNoLike(idNo));
    }
    if (StringUtils.isNotBlank(name)) {
      spec = spec.or(Spec.nameLike(name));
    }
    return findAll(spec, pageable);
  }

  class Spec {
    public static final QuerySpecification<Student> idNoLike(String idNo) {
      return (root, query, criteriaBuilder) ->
          criteriaBuilder.like(root.get("idNo"), "%" + idNo + "%");
    }

    public static final QuerySpecification<Student> nameLike(String name) {
      return (root, query, criteriaBuilder) ->
          criteriaBuilder.like(
              criteriaBuilder.upper(root.get("name")), "%" + name.toUpperCase() + "%");
    }
  }
}
