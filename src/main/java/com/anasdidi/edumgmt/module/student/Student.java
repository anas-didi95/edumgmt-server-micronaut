/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.module.student;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Serdeable
@Entity
@Table(name = "T_STUDENT")
public record Student(@Id @Column(name = "ID") UUID id, @Column(name = "NM") String name) {}
