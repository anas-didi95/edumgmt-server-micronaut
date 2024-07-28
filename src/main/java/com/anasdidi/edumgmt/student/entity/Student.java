/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "T_STUDENT")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "ID")
  private UUID id;

  @Column(name = "NM")
  private String name;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
