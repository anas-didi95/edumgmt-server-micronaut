/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest(environments = "test-exception")
public class ExceptionControllerTest {

  @Inject
  @Client("/edumgmt/exception")
  private HttpClient httpClient;

  @Test
  void testRecordNotFoundError() {
    try {
      httpClient.toBlocking().exchange(HttpRequest.GET("/recordNotFoundError"));
    } catch (HttpClientResponseException e) {
      assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
      assertEquals("Record not found!", e.getLocalizedMessage());
    }
  }
}
