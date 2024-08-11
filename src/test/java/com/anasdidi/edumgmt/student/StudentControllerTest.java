/* (C) 2024 Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.edumgmt.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.anasdidi.edumgmt.common.BaseControllerTest;
import com.anasdidi.edumgmt.student.dto.CreateStudentDTO;
import com.anasdidi.edumgmt.student.dto.DeleteStudentDTO;
import com.anasdidi.edumgmt.student.dto.UpdateStudentDTO;
import com.anasdidi.edumgmt.student.dto.ViewStudentDTO;
import com.anasdidi.edumgmt.student.entity.Student;
import com.anasdidi.edumgmt.student.repository.StudentRepository;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.json.JsonMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@MicronautTest(transactional = false)
public class StudentControllerTest extends BaseControllerTest {

  @Inject
  @Client("/edumgmt/student")
  private HttpClient httpClient;

  @Inject private JsonMapper jsonMapper;
  @Inject private StudentRepository studentRepository;

  @BeforeEach
  void beforeEach() {
    setModuleTest(ModuleTest.STUDENT);
    studentRepository.deleteAll();
  }

  @ParameterizedTest
  @CsvSource({"createStudent-input.json"})
  void testCreateStudentSuccess(String input) {
    CreateStudentDTO reqBody = null;

    try (InputStream iFile = getFile(input)) {
      reqBody = jsonMapper.readValue(iFile, CreateStudentDTO.class);
    } catch (Exception e) {
      fail(e);
    }

    HttpResponse<ViewStudentDTO> response =
        httpClient.toBlocking().exchange(HttpRequest.POST("", reqBody), ViewStudentDTO.class);
    assertEquals(HttpStatus.CREATED, response.getStatus());

    ViewStudentDTO resBody = response.body();
    assertNotNull(resBody.id());
    assertNotNull(resBody.createdBy());
    assertNotNull(resBody.createdDate());
    assertEquals(resBody.createdBy(), resBody.updatedBy());
    assertEquals(resBody.createdDate(), resBody.updatedDate());
    assertEquals(0, resBody.version());
    assertEquals(false, resBody.isDeleted());
    assertEquals(reqBody.name().toUpperCase(), resBody.name());
    assertEquals(reqBody.icNo(), resBody.icNo());
  }

  @ParameterizedTest
  @CsvSource({"createStudent-input.json"})
  void testViewStudentSuccess(String file) {
    Student entity = null;

    try (InputStream is = getFile(file)) {
      entity = jsonMapper.readValue(is, Student.class);
      entity = studentRepository.save(entity);
    } catch (Exception e) {
      fail(e);
    }

    HttpResponse<ViewStudentDTO> response =
        httpClient
            .toBlocking()
            .exchange(HttpRequest.GET("/" + entity.getId()), ViewStudentDTO.class);
    assertEquals(HttpStatus.OK, response.status());
    assertEquals(entity.getId(), response.body().id());
  }

  @ParameterizedTest
  @CsvSource({"createStudent-input.json"})
  void testUpdateStudentSuccess(String file) {
    Student entity = null;

    try (InputStream is = getFile(file)) {
      entity = jsonMapper.readValue(is, Student.class);
      entity = studentRepository.save(entity);
    } catch (Exception e) {
      fail(e);
    }

    String newName = "abc" + System.currentTimeMillis();
    String newIcNo = "010203040506";
    UpdateStudentDTO reqBody = new UpdateStudentDTO(entity.getId(), newIcNo, newName, 0);
    HttpResponse<ViewStudentDTO> response =
        httpClient
            .toBlocking()
            .exchange(HttpRequest.PUT("/" + entity.getId(), reqBody), ViewStudentDTO.class);
    assertEquals(HttpStatus.OK, response.status());

    ViewStudentDTO resBody = response.body();
    assertNotNull(resBody.updatedBy());
    assertTrue(resBody.updatedDate().compareTo(resBody.createdDate()) > 0);
    assertEquals(1, resBody.version());
    assertEquals(newName.toUpperCase(), resBody.name());
    assertEquals(newIcNo, resBody.icNo());
  }

  @ParameterizedTest
  @CsvSource({"createStudent-input.json"})
  void testDeleteStudentSuccess(String file) {
    Student entity = null;

    try (InputStream is = getFile(file)) {
      entity = jsonMapper.readValue(is, Student.class);
      entity = studentRepository.save(entity);
    } catch (Exception e) {
      fail(e);
    }

    DeleteStudentDTO reqBody = new DeleteStudentDTO(entity.getId(), 0);
    HttpResponse<Void> response =
        httpClient
            .toBlocking()
            .exchange(HttpRequest.DELETE("/" + entity.getId(), reqBody), Void.class);
    assertEquals(HttpStatus.NO_CONTENT, response.status());

    entity = studentRepository.findById(entity.getId()).get();
    assertNotNull(entity.getUpdatedBy());
    assertTrue(entity.getUpdatedDate().compareTo(entity.getCreatedDate()) > 0);
    assertEquals(1, entity.getVersion());
    assertEquals(true, entity.getIsDeleted());
  }
}
