/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.repository;

import com.anasdidi.edumgmt.auth.entity.User;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends PageableRepository<User, UUID> {

  Optional<User> findByUserIdAndIsDeleted(String userId, Boolean isDeleted);
}
