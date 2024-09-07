/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.common.util;

import io.micronaut.data.model.Sort;
import io.micronaut.data.model.Sort.Order;

public final class CommonConstants {

  public static final String SYSTEM_USER = "SYSTEM";

  public static final String SEARCH_DEFAULT_PAGE = "1";
  public static final String SEARCH_DEFAULT_SIZE = "10";
  public static final Sort SEARCH_DEFAULT_SORT = Sort.of(Order.asc("id"));
}
