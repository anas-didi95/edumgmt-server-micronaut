/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.repository;

import com.anasdidi.edumgmt.auth.entity.User;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.repository.PageableRepository;
import io.micronaut.data.repository.jpa.JpaSpecificationExecutor;
import io.micronaut.data.repository.jpa.criteria.QuerySpecification;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

@Repository
public interface UserRepository
    extends PageableRepository<User, UUID>, JpaSpecificationExecutor<User> {

  Optional<User> findByUserIdAndIsDeleted(String userId, Boolean isDeleted);

  default Page<User> searchUser(String userId, String name, Pageable pageable) {
    QuerySpecification<User> spec = (root, query, criteriaBuilder) -> null;
    if (StringUtils.isNotBlank(userId)) {
      spec = spec.or(Spec.userIdLike(userId));
    }
    if (StringUtils.isNotBlank(name)) {
      spec = spec.or(Spec.nameLike(name));
    }
    return findAll(spec, pageable);
  }

  class Spec {
    public static final QuerySpecification<User> userIdLike(String userId) {
      return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("userId"), userId);
    }

    public static final QuerySpecification<User> nameLike(String name) {
      return (root, query, criteriaBuilder) ->
          criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), name.toUpperCase());
    }
  }
}
