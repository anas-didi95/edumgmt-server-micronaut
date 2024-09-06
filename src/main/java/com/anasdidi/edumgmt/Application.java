/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt;

import io.micronaut.openapi.annotation.OpenAPISecurity;
import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
    info =
        @Info(
            title = "edumgmt",
            version = "0.1.0",
            description = "Education Management System",
            contact =
                @Contact(name = "Anas Juwaidi Bin Mohd Jeffry", email = "anas.didi95@gmail.com")),
    security = {@SecurityRequirement(name = "jwt")})
@SecuritySchemes(
    @SecurityScheme(
        name = "jwt",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"))
@OpenAPISecurity(tags = @Tag(name = "auth"))
public class Application {

  public static void main(String[] args) {
    Micronaut.run(Application.class, args);
  }
}
