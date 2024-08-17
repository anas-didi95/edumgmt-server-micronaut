/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.repository;

import com.anasdidi.edumgmt.auth.entity.UserToken;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserTokenRepository extends CrudRepository<UserToken, UUID> {

  Optional<UserToken> findByToken(String token);

  Number updateByUserId(String userId, Boolean isDeleted);
}
