/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import com.anasdidi.edumgmt.auth.dto.CreateUserDTO;
import com.anasdidi.edumgmt.auth.dto.ViewUserDTO;
import com.anasdidi.edumgmt.auth.repository.UserRepository;
import com.anasdidi.edumgmt.common.client.TestClient;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.json.JsonMapper;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.render.BearerAccessRefreshToken;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@MicronautTest(transactional = false)
class UserControllerTest {

  @Inject
  @Client("/edumgmt/user")
  private HttpClient userClient;

  @Inject private TestClient testClient;
  @Inject private UserRepository userRepository;
  @Inject private JsonMapper jsonMapper;
  private String accessToken;

  @BeforeEach
  void beforeEach() {
    if (accessToken == null) {
      UsernamePasswordCredentials credentials =
          new UsernamePasswordCredentials("sherlock", "password");
      HttpResponse<BearerAccessRefreshToken> response = testClient.login(credentials);
      assertEquals(HttpStatus.OK, response.getStatus());
      accessToken = response.body().getAccessToken();
    }
    userRepository.deleteAll();
  }

  @ParameterizedTest
  @CsvSource({"createUser-input.json"})
  void testCreateUserSuccess(String input) {
    CreateUserDTO reqBody = null;

    try (InputStream iFile = getFile(input)) {
      reqBody = jsonMapper.readValue(iFile, CreateUserDTO.class);
    } catch (Exception e) {
      fail(e);
    }

    HttpResponse<ViewUserDTO> response =
        userClient
            .toBlocking()
            .exchange(HttpRequest.POST("", reqBody).bearerAuth(accessToken), ViewUserDTO.class);
    assertEquals(HttpStatus.CREATED, response.status());

    ViewUserDTO resBody = response.body();
    assertNotNull(resBody.id());
    assertNotNull(resBody.createdBy());
    assertNotNull(resBody.createdDate());
    assertEquals(resBody.createdBy(), resBody.updatedBy());
    assertEquals(resBody.createdDate(), resBody.updatedDate());
    assertEquals(0, resBody.version());
    assertEquals(false, resBody.isDeleted());
    assertEquals(reqBody.userId(), resBody.userId());
    assertEquals(reqBody.name().toUpperCase(), resBody.name());
  }

  private InputStream getFile(String fileName) {
    return this.getClass().getResourceAsStream(String.format("/testcase/auth/%s", fileName));
  }
}
