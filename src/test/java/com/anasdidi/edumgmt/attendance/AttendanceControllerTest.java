/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest(transactional = false)
public class AttendanceControllerTest {

  @Inject
  @Client("/edumgmt/attendance")
  private HttpClient httpClient;

  @Test
  void testHelloWorld() {
    HttpResponse<Void> response = httpClient.toBlocking().exchange(HttpRequest.GET(""));
    assertEquals(HttpStatus.OK, response.status());
  }
}
