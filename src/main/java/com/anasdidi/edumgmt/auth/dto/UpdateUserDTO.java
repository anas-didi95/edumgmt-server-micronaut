/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.dto;

import io.micronaut.serde.annotation.Serdeable;
import java.util.UUID;

@Serdeable
public record UpdateUserDTO(UUID id, Integer version, String name) implements IAuthDTO {}
