/* (C) 2024 */
package com.anasdidi;

import io.micronaut.http.annotation.*;

@Controller("/edumgmt")
public class EdumgmtController {

  @Get(uri = "/", produces = "text/plain")
  public String index() {
    return "Example Response";
  }
}
