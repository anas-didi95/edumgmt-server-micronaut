/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.common.util;

import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;

public final class CommonUtils {

  public static Pageable preparePagable(int page, int size) {
    return preparePagable(page, size, CommonConstants.SEARCH_DEFAULT_SORT);
  }

  public static Pageable preparePagable(int page, int size, Sort sort) {
    return Pageable.from(page - 1, size, sort);
  }
}
