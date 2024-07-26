/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.module.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.json.JsonMapper;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@MicronautTest(startApplication = true)
class StudentControllerTest {

  private static final String BASE_URL = "/edumgmt/student";

  @Inject
  @Client("/")
  private HttpClient httpClient;

  @Inject private JsonMapper jsonMapper;
  @Inject private StudentService<Student, Student> addStudent;

  @ParameterizedTest
  @CsvSource({"addStudent-input.json,addStudent-output.json"})
  void testAddStudentSuccess(String input, String output) throws IOException {
    Student requestBody = null;
    Student responseBody = null;

    try (InputStream is1 = this.getClass().getResourceAsStream(testcase(input));
        InputStream is2 = this.getClass().getResourceAsStream(testcase(output))) {
      requestBody = jsonMapper.readValue(is1, Student.class);
      responseBody = jsonMapper.readValue(is2, Student.class);
      when(addStudent.process(requestBody)).thenReturn(responseBody);
    } catch (Exception e) {
      Assertions.fail(e.getMessage(), e);
    }

    HttpResponse<Student> result =
        httpClient.toBlocking().exchange(HttpRequest.POST(BASE_URL, requestBody), Student.class);
    assertEquals(HttpStatus.CREATED, result.status());
    assertEquals(
        jsonMapper.writeValueAsString(responseBody), jsonMapper.writeValueAsString(result.body()));
    verify(addStudent).process(any());
  }

  @SuppressWarnings("unchecked")
  @MockBean(AddStudent.class)
  StudentService<Student, Student> addStudent() {
    return mock(StudentService.class);
  }

  String testcase(String s) {
    return "/testcase/module/student/" + s;
  }
}
