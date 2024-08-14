/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.dto;

import io.micronaut.serde.annotation.Serdeable;
import java.util.Set;

@Serdeable
public record CreateUserDTO(String userId, String password, String name, Set<String> roles)
    implements IAuthDTO {}
