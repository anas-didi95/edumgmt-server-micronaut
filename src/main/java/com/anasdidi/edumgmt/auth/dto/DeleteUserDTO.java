/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.dto;

import io.micronaut.serde.annotation.Serdeable;
import java.util.UUID;

@Serdeable
public record DeleteUserDTO(UUID id, Integer version) implements IAuthDTO {}
