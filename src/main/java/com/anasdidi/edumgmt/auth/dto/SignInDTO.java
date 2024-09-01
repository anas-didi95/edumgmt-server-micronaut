/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record SignInDTO(String userId, String password) implements IAuthDTO {}
