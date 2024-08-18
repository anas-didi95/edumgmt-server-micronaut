/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.anasdidi.edumgmt.common.BaseControllerTest;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

@MicronautTest(environments = "test-exception")
public class ExceptionControllerTest extends BaseControllerTest {

  @Inject
  @Client("/edumgmt/exception")
  private HttpClient httpClient;

  @Test
  void testRecordNotFoundError_Success() {
    Executable e;
    HttpClientResponseException ex;
    MutableHttpRequest<?> req = HttpRequest.GET("/recordNotFoundError");

    e = () -> httpClient.toBlocking().exchange(req);
    ex = assertThrows(HttpClientResponseException.class, e);
    assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatus());

    e = () -> httpClient.toBlocking().exchange(req.bearerAuth(getAccessToken()));
    ex = assertThrows(HttpClientResponseException.class, e);
    assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    assertTrue(ex.getMessage().contains("Record not found!"));
  }

  @Test
  void testRecordNotMatchedError_Success() {
    Executable e;
    HttpClientResponseException ex;
    MutableHttpRequest<?> req = HttpRequest.GET("/recordNotMatchedError");

    e = () -> httpClient.toBlocking().exchange(req);
    ex = assertThrows(HttpClientResponseException.class, e);
    assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatus());

    e = () -> httpClient.toBlocking().exchange(req.bearerAuth(getAccessToken()));
    ex = assertThrows(HttpClientResponseException.class, e);
    assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    assertEquals("Record not matched!", ex.getMessage());
  }
}
