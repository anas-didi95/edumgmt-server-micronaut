/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.anasdidi.edumgmt.auth.entity.User;
import com.anasdidi.edumgmt.auth.repository.UserRepository;
import com.anasdidi.edumgmt.auth.util.UserConstants;
import com.anasdidi.edumgmt.common.factory.CommonProps;
import com.anasdidi.edumgmt.common.util.Constant;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.json.JsonMapper;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@MicronautTest
class EdumgmtTest {

  @Inject
  @Client("/edumgmt")
  private HttpClient httpClient;

  @Inject private EmbeddedApplication<?> application;
  @Inject private JsonMapper jsonMapper;
  @Inject private CommonProps commonProps;
  @Inject private UserRepository userRepository;

  @Test
  void testItWorks() {
    Assertions.assertTrue(application.isRunning());
  }

  @Test
  void testSwaggerUISuccess() {
    MutableHttpRequest<?> req =
        HttpRequest.GET("/swagger-ui/index.html").accept(MediaType.TEXT_HTML_TYPE);

    Executable e = () -> httpClient.toBlocking().exchange(req);
    HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, e);
    assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatus());

    HttpResponse<?> response =
        httpClient
            .toBlocking()
            .exchange(
                req.basicAuth(
                    commonProps.getBasicAuth().username(), commonProps.getBasicAuth().password()));
    assertEquals(HttpStatus.OK, response.status());
  }

  @Test
  void testCommonPropsSuccess() {
    assertEquals("testUsername", commonProps.getBasicAuth().username());
    assertEquals("testPassword", commonProps.getBasicAuth().password());
  }

  @Test
  void testOnStartupEventSuccess() {
    assertTrue(application.isRunning());

    Optional<User> result =
        userRepository.findByUserIdAndIsDeleted(UserConstants.SuperAdmin.ID, false);
    assertTrue(result.isPresent());

    User user = result.get();
    assertEquals(Constant.SYSTEM_USER, user.getCreatedBy());
  }

  @ParameterizedTest
  @CsvSource({"sample-input.json,sample-output.json"})
  void testReadSampleTestCase(String inputFile, String outputFile) {
    Assertions.assertEquals("sample-input.json", inputFile);
    Assertions.assertEquals("sample-output.json", outputFile);

    try (InputStream is = EdumgmtTest.class.getResourceAsStream("/testcase/" + inputFile);
        InputStream os = EdumgmtTest.class.getResourceAsStream("/testcase/" + outputFile)) {
      Map<String, String> input =
          jsonMapper.readValue(is, Argument.mapOf(Argument.STRING, Argument.STRING));
      Assertions.assertEquals("sample-input", input.get("id"));
      Assertions.assertEquals("HELLO WORLD", input.get("value"));

      Map<String, String> output =
          jsonMapper.readValue(os, Argument.mapOf(Argument.STRING, Argument.STRING));
      Assertions.assertEquals("sample-output", output.get("id"));
      Assertions.assertEquals("HI!", output.get("value"));
    } catch (Exception e) {
      Assertions.fail(e);
    }
  }
}
