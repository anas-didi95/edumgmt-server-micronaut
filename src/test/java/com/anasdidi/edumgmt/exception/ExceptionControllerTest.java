/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.anasdidi.edumgmt.auth.client.AuthClient;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.render.BearerAccessRefreshToken;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

@MicronautTest(environments = "test-exception")
public class ExceptionControllerTest {

  @Inject
  @Client("/edumgmt/exception")
  private HttpClient httpClient;

  @Inject private AuthClient authClient;
  private String accessToken;

  @BeforeEach
  void beforeEach() {
    accessToken =
        Optional.ofNullable(accessToken)
            .orElseGet(
                () -> {
                  UsernamePasswordCredentials credentials =
                      new UsernamePasswordCredentials("sherlock", "password");
                  HttpResponse<BearerAccessRefreshToken> response = authClient.login(credentials);
                  assertEquals(HttpStatus.OK, response.getStatus());
                  return response.body().getAccessToken();
                });
  }

  @Test
  void testRecordNotFoundError() {
    Executable e;
    HttpClientResponseException ex;
    MutableHttpRequest<?> req = HttpRequest.GET("/recordNotFoundError");

    e = () -> httpClient.toBlocking().exchange(req);
    ex = assertThrows(HttpClientResponseException.class, e);
    assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatus());

    e = () -> httpClient.toBlocking().exchange(req.bearerAuth(accessToken));
    ex = assertThrows(HttpClientResponseException.class, e);
    assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    assertEquals("Record not found!", ex.getMessage());
  }

  @Test
  void testRecordNotMatchedError() {
    Executable e;
    HttpClientResponseException ex;
    MutableHttpRequest<?> req = HttpRequest.GET("/recordNotMatchedError");

    e = () -> httpClient.toBlocking().exchange(req);
    ex = assertThrows(HttpClientResponseException.class, e);
    assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatus());

    e = () -> httpClient.toBlocking().exchange(req.bearerAuth(accessToken));
    ex = assertThrows(HttpClientResponseException.class, e);
    assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    assertEquals("Record not matched!", ex.getMessage());
  }
}
