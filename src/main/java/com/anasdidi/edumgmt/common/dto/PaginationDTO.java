/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.common.dto;

import io.micronaut.data.model.Page;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record PaginationDTO(
    Integer page, Integer totalPages, Integer recordsPerPage, Long totalRecords) {

  public PaginationDTO(Page<?> page) {
    this(page.getPageNumber() + 1, page.getTotalPages(), page.getSize(), page.getTotalSize());
  }
}
