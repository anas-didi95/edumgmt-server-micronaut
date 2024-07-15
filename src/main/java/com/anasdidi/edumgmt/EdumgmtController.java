/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt;

import io.micronaut.http.annotation.*;

@Controller("/edumgmt")
public class EdumgmtController {

  @Get(uri = "/", produces = "text/plain")
  public String index() {
    return "Example Response";
  }
}
