/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.anasdidi.edumgmt.student.dto.CreateStudentDTO;
import com.anasdidi.edumgmt.student.dto.ViewStudentDTO;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.json.JsonMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.io.InputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@MicronautTest
public class StudentControllerTest {

  @Inject
  @Client("/edumgmt/student")
  private HttpClient httpClient;

  @Inject private JsonMapper jsonMapper;

  @ParameterizedTest
  @CsvSource({"createStudent-input.json"})
  void testCreateStudentSuccess(String input) {
    CreateStudentDTO reqBody = null;

    try (InputStream iFile = getFile(input)) {
      reqBody = jsonMapper.readValue(iFile, CreateStudentDTO.class);
    } catch (Exception e) {
      Assertions.fail(e);
    }

    HttpResponse<ViewStudentDTO> response =
        httpClient.toBlocking().exchange(HttpRequest.POST("", reqBody), ViewStudentDTO.class);
    assertEquals(HttpStatus.CREATED, response.getStatus());

    ViewStudentDTO resBody = response.body();
    assertNotNull(resBody.id());
    assertEquals(reqBody.name().toUpperCase(), resBody.name());
  }

  private InputStream getFile(String fileName) {
    return this.getClass().getResourceAsStream(String.format("/testcase/student/%s", fileName));
  }
}
